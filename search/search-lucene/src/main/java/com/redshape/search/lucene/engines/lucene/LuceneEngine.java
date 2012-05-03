package com.redshape.search.lucene.engines.lucene;

import com.redshape.search.annotations.Searchable;
import com.redshape.search.collectors.ICollectorsBuilder;
import com.redshape.search.collectors.IResultsCollector;
import com.redshape.search.engines.EngineException;
import com.redshape.search.engines.ISearchEngine;
import com.redshape.search.index.IIndex;
import com.redshape.search.index.IIndexField;
import com.redshape.search.index.IIndexObjectIdGenerator;
import com.redshape.search.index.builders.IIndexBuilder;
import com.redshape.search.query.terms.ISearchTerm;
import com.redshape.search.query.transformers.ITransformersBuilder;
import com.redshape.search.lucene.query.transformers.LuceneQueryTransformer;
import com.redshape.search.serializers.ISerializer;
import com.redshape.search.serializers.ISerializersFacade;
import com.redshape.search.serializers.SerializerException;
import com.redshape.utils.beans.PropertyUtils;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
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
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:52:27 PM
 * To change this template use File | Settings | File Templates.
 */
public class LuceneEngine implements ISearchEngine {
	public static final Version VERSION = Version.LUCENE_30;
	public static String[] ESCAPE_SEQUENCES = new String[] {
									"+", "-", "&&", "||", "!", "(", ")",
									"{", "}", "[", "]", "^", "\"", "~", "*", "?", ":", "\\"
								};

	@Autowired( required = true )
	private ITransformersBuilder transformersBuilder;

	@Autowired( required = true )
	private IIndexBuilder indexBuilder;

	@Autowired( required = true )
	private ISerializersFacade serializersFacade;

	@Autowired( required = true )
	private IIndexObjectIdGenerator idGenerator;

	@Autowired( required = true )
	private ICollectorsBuilder collectorBuilder;

	@Autowired( required = true )
	private IConfig config;

	public ITransformersBuilder getTransformersBuilder() {
		return transformersBuilder;
	}

	public void setTransformersBuilder(ITransformersBuilder transformersBuilder) {
		this.transformersBuilder = transformersBuilder;
	}

	public ISerializersFacade getSerializersFacade() {
		return serializersFacade;
	}

	public void setSerializersFacade(ISerializersFacade serializersFacade) {
		this.serializersFacade = serializersFacade;
	}

	public IIndexObjectIdGenerator getIdGenerator() {
		return idGenerator;
	}

	public void setIdGenerator(IIndexObjectIdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	public ICollectorsBuilder getCollectorBuilder() {
		return collectorBuilder;
	}

	public void setCollectorBuilder(ICollectorsBuilder collectorBuilder) {
		this.collectorBuilder = collectorBuilder;
	}

	public IIndexBuilder getIndexBuilder() {
		return indexBuilder;
	}

	public void setIndexBuilder(IIndexBuilder indexBuilder) {
		this.indexBuilder = indexBuilder;
	}

	public void setConfig( IConfig config ) {
		this.config = config;
	}
	
	protected IConfig getConfig() {
		return this.config;
	}

	protected void checkAssertions( Class<?> object) {
		if ( !this.isSupported(object) ) {
			throw new IllegalArgumentException("Given object not supported");
		}
	}

	private boolean isSupported( Class<?> object ) {
		return object.getAnnotation(Searchable.class) != null;
	}

    public void save( Object searchable ) throws EngineException {
        try {
			this.checkAssertions(searchable.getClass());

            IIndex index = this.getIndexBuilder().getIndex(searchable.getClass());

            IndexWriter writer = this.createIndexWriter( index );
            writer.addDocument(  this.createDocument( searchable, index ) );
            writer.commit();
            writer.close();
        } catch( Throwable e ) {
            throw new EngineException( e.getMessage(), e );
        }
    }

    public void remove( Object searchable ) throws EngineException {
        try {
			this.checkAssertions(searchable.getClass());

            IIndex index = this.getIndexBuilder().getIndex(searchable.getClass());

            IndexReader reader = IndexReader.open( this.openDirectory( this.getIndexDirectoryPath(index) ) );
            reader.deleteDocuments( new Term("searchable_entity", this.createDocumentId(searchable) ) );
            reader.close();
        } catch ( Throwable e ) {
            throw new EngineException( e.getMessage(), e );
        }
    }

    public <T> Collection<T> find( Class<? extends T> searchable, ISearchTerm query ) throws EngineException {
        try {
			this.checkAssertions(searchable);

			IResultsCollector collector = this.getCollectorBuilder().createCollector();

            IIndex index = this.getIndexBuilder().getIndex(searchable);
            IndexSearcher searcher = new IndexSearcher( this.openDirectory( index ) );
            Query searchQuery =  this.getTransformersBuilder()
									 .getTransformer(LuceneQueryTransformer.class)
							.transform(query);

			TopDocs result = searcher.search(searchQuery, null, 1000);
            ScoreDoc[] hits = result.scoreDocs;
            for ( ScoreDoc hit : hits ) {
                Document doc = searcher.doc( hit.doc );
                String docId = doc.getField("searchable_entity").stringValue();

				collector.collect(
					this.getEntityClassByDocId( docId ),
					this.prepareFieldsMap(index, doc),
					this.getEntityIdByDocId(docId)
				);
            }

            return collector.getResults();
        } catch ( Throwable e ) {
            throw new EngineException( e.getMessage(), e );
        }
    }

	protected String getEntityIdByDocId( String docId ) {
		return docId.split("_")[1];
	}

	private Map<IIndexField, Object> prepareFieldsMap( IIndex index, Document doc  )
		throws SerializerException {
		Map<IIndexField, Object> fields = new HashMap<IIndexField, Object>();
		for ( IIndexField field : index.getFields() ) {
			Field docField = doc.getField( field.getName() );
			if ( docField == null ) {
				continue;
			}

			fields.put( field, field.isBinary() ? this.unserialize(field, docField.getBinaryValue() )
												: this.unserialize(field, docField.stringValue() ) );
		}

		return fields;
	}

	private Object unserialize( IIndexField indexField, Object value )
			throws SerializerException {
		ISerializer serializer = this.getSerializersFacade()
			   .getSerializer(indexField.getSerializer());

		if ( serializer != null ) {
			if ( value.getClass().isArray() ) {
				return serializer.unserialize( (byte[]) value );
			} else {
				return serializer.unserialize( (String) value );
			}
		} else {
			return value;
		}
	}


    protected String createDocumentId( Object searchable ) {
        StringBuilder builder = new StringBuilder();
        builder.append( searchable.getClass().getCanonicalName() )
               .append( "_" )
               .append( this.getIdGenerator().generate( searchable ) );

        return builder.toString();
    }


    @SuppressWarnings("unchecked")
	private Class<?> getEntityClassByDocId( String docId ) throws ClassNotFoundException {
		/**
		 * @TODO Move class-loading logic to class loader
		 */
        return Class.forName( docId.split("_")[0] );
    }

    protected IndexWriter createIndexWriter( IIndex index ) throws EngineException {
		Directory directory = null;
        try {
			Analyzer analyzer = new StandardAnalyzer(VERSION);

            // Store the index in memory:
            directory = this.openDirectory( this.getIndexDirectoryPath( index ) );

            IndexWriter writer = new IndexWriter(directory, analyzer, true, new IndexWriter.MaxFieldLength(25000));

			writer.setWriteLockTimeout(500);

			return writer;
        } catch ( Throwable e ) {
			if ( directory != null ) {
				try {
					directory.clearLock(IndexWriter.WRITE_LOCK_NAME);
					directory.close();
				} catch ( IOException ex ) {
					throw new EngineException( e.getMessage(), ex );
				}
			}

            throw new EngineException("Cannot create search index writer!", e);
        }
    }

    protected Directory openDirectory( IIndex index ) throws IOException, ConfigException {
        return this.openDirectory( this.getIndexDirectoryPath(index) );
    }

    protected Directory openDirectory( String path ) throws IOException {
        return this.openDirectory( new File(path) );
    }

    protected Directory openDirectory( File file ) throws IOException {
        return FSDirectory.open( file );
    }

    protected String getIndexDirectoryPath( IIndex index ) throws ConfigException, IOException {
        File file = new File( this.getConfig().get("search").get("indexPath").value() + "/" + index.getName() );
		if ( !file.exists() ) {
			file.mkdir();
		} else if ( file.exists() ) {
			if ( !file.canWrite() || !file.canRead() || !file.isDirectory() ) {
				throw new ConfigException("Search index path " + file.getAbsolutePath()
						+ " is inaccessible or is not directory!");
			}
		}

		return file.getAbsolutePath();
    }

    private Document createDocument( Object searchable, IIndex index ) throws EngineException {
        try {
            Document doc = new Document();
            doc.add( new Field( "searchable_entity", this.createDocumentId(searchable), Field.Store.YES, Field.Index.NO ) );

            for ( IIndexField field : index.getFields() ) {
                Object fieldValue = PropertyUtils.getInstance()
												.getProperty(
												 	searchable.getClass(), field.getName()
												).get(searchable);

                Field docField;
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
            throw new EngineException( e.getMessage(), e );
        }
    }

    protected Field processBinaryField( IIndexField field, Object value ) throws EngineException {
        return new Field(
            field.getName(),
            this.serializeBytes( field, value ),
            field.isStored() ? Field.Store.YES : Field.Store.NO
        );
    }

    protected Field processField( IIndexField field, Object value ) throws EngineException {
        return new Field(
            field.getName(),
            this.serialize( field, value ),
            field.isStored() ? Field.Store.YES : Field.Store.NO,
            field.isAnalyzable() ? Field.Index.ANALYZED : Field.Index.NO
        );
    }

    protected byte[] serializeBytes( IIndexField field, Object value ) throws EngineException {
        try {
            @SuppressWarnings("unchecked")
			ISerializer serializer = this.getSerializersFacade().getSerializer(field.getSerializer());
            if ( serializer != null ) {
                return serializer.serializeBytes(value);
            }

            if ( !Serializable.class.isAssignableFrom( value.getClass() ) ) {
                throw new EngineException("Binary fields must be serializable to be processed");
            }

            try {
                return getBytes(value);
            } catch ( IOException e ) {
                throw new EngineException("Cannot convert object");
            }
        } catch ( EngineException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new EngineException( e.getMessage(), e );
        }
    }

    @SuppressWarnings("unchecked")
	protected String serialize( IIndexField field, Object value ) throws EngineException {
        try {
            return field.getSerializer() == null ? String.valueOf( value ) :
									this.getSerializersFacade()
											.getSerializer(field.getSerializer())
											.serializeString(value);
        } catch ( Throwable e ) {
            throw new EngineException( e.getMessage(), e );
        }
    }

    protected static byte[] getBytes(Object obj) throws java.io.IOException{
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(obj);
        oos.flush();
        oos.close();
        bos.close();

        return bos.toByteArray();
    }

}
