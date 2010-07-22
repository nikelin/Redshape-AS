package com.vio.search.query;

import com.vio.search.serializers.ISerializer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:57:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexField {
    private String name;
    private IndexingType type;
    private Class<? extends ISerializer> serializer;
    private int rank;
    private boolean binary;
    private boolean stored;
    private boolean analyzable;

    public void setRank( int rank ) {
        this.rank = rank;
    }

    public int getRank() {
        return this.rank;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public IndexingType getType() {
        return this.type;
    }

    public void setType( IndexingType type ) {
        this.type = type;
    }

    public void markBinary( boolean state ) {
        this.binary = state;
    }

    public boolean isBinary() {
        return this.binary;
    }

    public void markStored( boolean state ) {
        this.stored = state;
    }

    public boolean isStored() {
        return this.stored;
    }

    public boolean isAnalyzable() {
        return this.analyzable;
    }

    public void markAnalyzable( boolean state ) {
        this.analyzable = state;
    }

    public Class<? extends ISerializer> getSerializer() {
        return this.serializer;
    }

    public void setSerializer( Class<? extends ISerializer> serializer ) {
        this.serializer = serializer;
    }
}
