package com.vio.search.query;

import com.vio.search.ISearchable;
import com.vio.search.annotations.Searchable;
import com.vio.search.annotations.SearchableField;
import com.vio.search.annotations.SearchableFieldSerializer;

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
public class IndexBuilder {
    private static Class<? extends IndexBuilder> defaultBuilder = IndexBuilder.class;

    private Map< Class<? extends ISearchable>, Index> indexes = new HashMap();

    public static void setDefaultBuilder( Class<? extends IndexBuilder> builderClazz ) {
        defaultBuilder = builderClazz;
    }

    public static IndexBuilder newBuilder() throws InstantiationException {
        return newBuilder( defaultBuilder );
    }

    public static IndexBuilder newBuilder( Class<? extends IndexBuilder> builder ) throws InstantiationException {
        try {
            return defaultBuilder.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

    public Index getIndex( Class<? extends ISearchable> searchable ) {
        Index index = this.getIndex( searchable);
        if ( index != null ) {
            return index;
        }

        index = this.buildIndex( searchable );

        this.indexes.put( searchable, index );

        return index;
    }

    protected Index buildIndex( Class<? extends ISearchable> searchable ) {
        Index index = new Index();

        Searchable meta = searchable.getClass().getAnnotation( Searchable.class );
        if ( meta == null ) {
            return null;
        }

        index.setName( meta.name() );

        for ( Field field : searchable.getFields() ) {
            IndexField indexPart = new IndexField();

            SearchableField fieldMeta = field.getAnnotation( SearchableField.class );
            indexPart.setName( fieldMeta.name() );
            indexPart.setType( fieldMeta.type() );
            indexPart.setRank( fieldMeta.rank() );
            indexPart.markAnalyzable( fieldMeta.analyzable() );
            indexPart.markBinary( fieldMeta.binary() );
            indexPart.markStored( fieldMeta.stored() );

            SearchableFieldSerializer serializerMeta = field.getAnnotation( SearchableFieldSerializer.class );
            if ( serializerMeta != null ) {
                indexPart.setSerializer( serializerMeta.serializer() );
            }

            index.addField( indexPart );
        }

        return index;
    }

}
