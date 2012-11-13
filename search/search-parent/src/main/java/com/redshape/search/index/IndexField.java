/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.search.index;

import com.redshape.search.serializers.ISerializer;
import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:57:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexField implements IIndexField {
    private String name;
    private String fieldName;
    private IndexingType type;
    private Class<? extends ISerializer> serializer;
    private int rank;
    private boolean binary;
    private boolean stored;
    private boolean analyzable;

    public IndexField( String fieldName ) {
        Commons.checkNotNull(fieldName);
        this.fieldName = fieldName;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

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
