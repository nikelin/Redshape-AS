package com.redshape.search.index;

import com.redshape.search.serializers.ISerializer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 2:59:12 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IIndexField {
    void setRank( int rank );

    int getRank();

    String getName();

    void setName( String name );

    IndexingType getType();

    void setType( IndexingType type );

    void markBinary( boolean state );

    boolean isBinary();

    void markStored( boolean state );

    boolean isStored();

    boolean isAnalyzable();

    void markAnalyzable( boolean state );

    Class<? extends ISerializer> getSerializer();

    void setSerializer( Class<? extends ISerializer> serializer );
}
