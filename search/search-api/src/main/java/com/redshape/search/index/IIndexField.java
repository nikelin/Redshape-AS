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
