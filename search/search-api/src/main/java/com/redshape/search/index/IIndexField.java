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
    public void setRank( int rank );

    public int getRank();

    public String getName();

    public void setName( String name );

    public IndexingType getType();

    public void setType( IndexingType type );

    public void markBinary( boolean state );

    public boolean isBinary();

    public void markStored( boolean state );

    public boolean isStored();

    public boolean isAnalyzable();

    public void markAnalyzable( boolean state );

    public String getFieldName();

    public Class<? extends ISerializer> getSerializer();

    public void setSerializer( Class<? extends ISerializer> serializer );
}
