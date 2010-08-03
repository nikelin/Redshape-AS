package com.vio.search.engines;

import com.vio.config.readers.ConfigReaderException;
import com.vio.search.*;
import com.vio.search.collectors.CollectorsFactory;
import com.vio.search.collectors.IResultsCollector;
import com.vio.search.query.Index;
import com.vio.search.query.IndexBuilder;
import com.vio.search.query.IndexField;
import com.vio.search.query.terms.ISearchTerm;
import com.vio.search.query.transformers.LuceneQueryTransformer;
import com.vio.search.query.transformers.TransformersFactory;
import com.vio.search.serializers.ISerializer;
import com.vio.utils.Registry;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:52:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class LuceneEngine implements ISearchEngine {

    public LuceneEngine() {
    }

    public void save( ISearchable searchable ) throws EngineException {
        try {
            Index index = IndexBuilder.newBuilder().getIndex( searchable.getClass() );

            IndexWriter writer = this.createIndexWriter( index );
            writer.addDocument(  this.createDocument( searchable, index ) );
            writer.commit();
            writer.close();
        } catch( Throwable e ) {
            throw new EngineException();
        }
    }

    public void remove( ISearchable searchable ) throws EngineException {
        try {
            Index index = IndexBuilder.newBuilder().getIndex( searchable.getClass() );

            IndexReader reader = IndexReader.open( this.openDirectory( this.getIndexDirectoryPath(index) ) );
            reader.deleteDocuments( new Term("searchable_entity", this.createDocumentId(searchable) ) );
            reader.close();
        } catch ( Throwable e ) {
            throw new EngineException();
        }
    }

    public <T extends ISearchable> Collection<T> find( Class<? extends T> searchable, ISearchTerm query ) throws EngineException {
        try {
            IResultsCollector collector = CollectorsFactory.getDefault().getCollector( searchable );

            Index index = IndexBuilder.newBuilder().getIndex( searchable );

            IndexSearcher searcher = new IndexSearcher( this.openDirectory( index ) );
            Query searchQuery =  TransformersFactory.getDefault().createTransformer(LuceneQueryTransformer.class).transform( query );

            ScoreDoc[] hits = searcher.search(searchQuery, null, 1000).scoreDocs;
            for ( ScoreDoc hit : hits ) {
                Document doc = searcher.doc( hit.doc );
                String docId = doc.getField("searchable_entity").stringValue();

                Class<? extends ISearchable> foundedClass = this.getEntityClassByDocId( docId );
                
            }

            return collector.getResults();
        } catch ( Throwable e ) {
            throw new EngineException();
        }
    }

    public String createDocumentId( ISearchable searchable ) {
        StringBuilder builder = new StringBuilder();
        builder.append( searchable.getClass().getCanonicalName() )
               .append( "_" )
               .append( searchable.getId() );

        return builder.toString();
    }

    private String getEntityIdByDocId( String id ) {
        return id.split("_")[1];
    }

    private Class<? extends ISearchable> getEntityClassByDocId( String docId ) throws ClassNotFoundException {
        return (Class<? extends ISearchable>) Class.forName( docId.split("_")[0] );
    }

    protected IndexWriter createIndexWriter( Index index ) throws EngineException {
        try {
            Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_CURRENT);

            // Store the index in memory:
            Directory directory = this.openDirectory( this.getIndexDirectoryPath( index ) );
            IndexWriter writer = new IndexWriter(directory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

            return writer;
        } catch ( Throwable e ) {
            throw new EngineException("Cannot create search index writer");
        }
    }

    protected Directory openDirectory( Index index ) throws ConfigReaderException, IOException {
        return this.openDirectory( this.getIndexDirectoryPath(index) );
    }

    protected Directory openDirectory( String path ) throws ConfigReaderException, IOException {
        return this.openDirectory( new File(path) );
    }

    protected Directory openDirectory( File file ) throws ConfigReaderException, IOException {
        return FSDirectory.open( file );
    }

    protected String getIndexDirectoryPath( Index index ) throws ConfigReaderException, IOException {
        return Registry.getApiServerConfig().getSearchIndexPath() + "/" + index.getName();
    }

    private Document createDocument( ISearchable searchable, Index index ) throws EngineException {
        try {
            Document doc = new Document();
            doc.add( new Field( "searchable_entity", this.createDocumentId(searchable), Field.Store.YES, Field.Index.NO ) );

            for ( IndexField field : index.getFields() ) {
                Object fieldValue = field.getClass().getField( field.getName() );

                Field docField = null;
                if ( field.isBinary() ) {
                    docField = this.processBinaryField( field, fieldValue );
                } else {
                    docField = this.processField( field, fieldValue );
                }

                if ( docField == null ) {
                    throw new EngineException("Cannot handle field type! (" + field.getName() + ")");
                }

                doc.add(docField);
            }

            return doc;
        } catch ( Throwable e ) {
            throw new EngineException();
        }
    }

    protected Field processBinaryField( IndexField field, Object value ) throws EngineException {
        return new Field(
            field.getName(),
            this.serializeBytes( field, value ),
            field.isStored() ? Field.Store.YES : Field.Store.NO
        );
    }

    protected Field processField( IndexField field, Object value ) throws EngineException {
        return new Field(
            field.getName(),
            this.serialize( field, value ),
            field.isStored() ? Field.Store.YES : Field.Store.NO,
            field.isAnalyzable() ? Field.Index.ANALYZED : Field.Index.NO
        );
    }

    protected byte[] serializeBytes( IndexField field, Object value ) throws EngineException {
        try {
            ISerializer serializer = Search.getSerializer( field.getSerializer() );
            if ( serializer != null ) {
                return serializer.serializeBytes(value);
            }

            if ( !Serializable.class.isAssignableFrom( value.getClass() ) ) {
                throw new EngineException("Binary fields must be serializable to be processed");
            }

            try {
                return getbytes( value );
            } catch ( IOException e ) {
                throw new EngineException("Cannot convert object");
            }
        } catch ( EngineException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new EngineException();
        }
    }

    protected String serialize( IndexField field, Object value ) throws EngineException {
        try {
            return field.getSerializer() == null ? String.valueOf( value ) : Search.getSerializer( field.getSerializer() ).serializeString(value);
        } catch ( Throwable e ) {
            throw new EngineException();
        }
    }

    public static byte[] getbytes(Object obj) throws java.io.IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();

        return bos.toByteArray();
    }

}