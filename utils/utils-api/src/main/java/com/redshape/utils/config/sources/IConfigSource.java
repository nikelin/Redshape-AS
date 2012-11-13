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

package com.redshape.utils.config.sources;

import com.redshape.utils.config.ConfigException;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:26 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IConfigSource extends Serializable {

    public interface OnChangeCallback {

        public void onChanged();

    }

    public void setCallback( OnChangeCallback callback );

    public void markClean();

    public void reload();

    public boolean isChanged();

    public String read() throws ConfigException;
    
    public void write( String data ) throws ConfigException;
    
    public boolean isWritable();
    
    public boolean isReadable();
    
}
