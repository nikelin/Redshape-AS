package com.vio.search.index.builders;

import com.vio.search.ISearchable;
import com.vio.search.annotations.Searchable;
import com.vio.search.index.IIndex;
import com.vio.search.index.Index;
import com.vio.search.index.visitor.VisitorException;
import com.vio.search.index.visitor.field.StandardFieldVisitor;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 4:06:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexBuilder extends AbstractIndexBuilder {
    private static final Logger log = Logger.getLogger( IndexBuilder.class );
    private static Class<? extends IIndexBuilder> defaultBuilder = IndexBuilder.class;

    private Map< Class<? extends ISearchable>, IIndex> indexes = new HashMap();
    
    public IndexBuilder( Class<? extends IndexBuilder> clazz ) {
        this.setFieldVisitor( new StandardFieldVisitor() );
    }

    public static void setDefaultBuilder( Class<? extends IIndexBuilder> builderClazz ) {
        defaultBuilder = builderClazz;
    }

    public static IIndexBuilder newBuilder() throws InstantiationException {
        return newBuilder( defaultBuilder );
    }

    public static IIndexBuilder newBuilder( Class<? extends IIndexBuilder> builder ) throws InstantiationException {
        try {
            return defaultBuilder.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    @Override
    public IIndex getIndex( Class<? extends ISearchable> searchable ) throws BuilderException {
        IIndex index = this.indexes.get( searchable);
        if ( index != null ) {
            return index;
        }

        index = this.buildIndex( searchable );

        this.indexes.put( searchable, index );

        return index;
    }

    protected IIndex buildIndex( Class<? extends ISearchable> searchable ) throws BuilderException {
        IIndex index = new Index();

        Searchable meta = searchable.getClass().getAnnotation( Searchable.class );
        if ( meta == null ) {
            return null;
        }

        index.setName( meta.name() );

        for ( Field field : searchable.getFields() ) {
            try {
                this.getFieldVisitor().visitField( index, searchable, field );
            } catch ( VisitorException e ) {
                log.error("Index builder exception", e );
                throw new BuilderException();
            }
        }

        return index;
    }

}
