package com.redshape.search.index;

import com.redshape.search.serializers.ISerializer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:57:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexField implements IIndexField {
    private String name;
    private IndexingType type;
    private Class<? extends ISerializer> serializer;
    private int rank;
    private boolean binary;
    private boolean stored;
    private boolean analyzable;

    @Override
    public void setRank( int rank ) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName( String name ) {
        this.name = name;
    }

    @Override
    public IndexingType getType() {
        return this.type;
    }

    @Override
    public void setType( IndexingType type ) {
        this.type = type;
    }

    @Override
    public void markBinary( boolean state ) {
        this.binary = state;
    }

    @Override
    public boolean isBinary() {
        return this.binary;
    }

    @Override
    public void markStored( boolean state ) {
        this.stored = state;
    }

    @Override
    public boolean isStored() {
        return this.stored;
    }

    @Override
    public boolean isAnalyzable() {
        return this.analyzable;
    }

    @Override
    public void markAnalyzable( boolean state ) {
        this.analyzable = state;
    }

    @Override
    public Class<? extends ISerializer> getSerializer() {
        return this.serializer;
    }

    @Override
    public void setSerializer( Class<? extends ISerializer> serializer ) {
        this.serializer = serializer;
    }
}
