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

import java.io.*;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class StreamingSource implements IConfigSource {
    private InputStream input;
    private OutputStream output;

    public StreamingSource( InputStream input ) {
        this(input, null);
    }
    
    public StreamingSource( InputStream input, OutputStream output ) {
        this.input = input;
        this.output = output;
    }

    @Override
    public void setCallback(OnChangeCallback callback) {
    }

    @Override
    public void markClean() {
    }

    @Override
    public void reload() {
        throw new IllegalStateException("Must be overridden");
    }

    @Override
    public boolean isChanged() {
        throw new IllegalStateException("Must be overridden");
    }

    @Override
    public String read() throws ConfigException {
        try {
            if ( !this.isReadable() ) {
                throw new IllegalStateException("Write-only source");
            }

            InputStreamReader reader = new InputStreamReader(this.input);
            StringBuilder builder = new StringBuilder();
            int read = 0;
            do {
                char[] data = new char[512];
                read = reader.read(data);

                if ( read > 0 ) {
                    builder.append( Arrays.copyOfRange(data, 0, read) );
                }
            } while ( read > 0 );

            return builder.toString();
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
        }
    }

    @Override
    public void write( String data) throws ConfigException {
        try {
            if ( !this.isWritable() ) {
                throw new IllegalStateException("Read-only source");
            }
    
            OutputStreamWriter writer = new OutputStreamWriter(this.output);
            writer.write(data);
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
        }
    }

    @Override
    public boolean isWritable() {
        return this.output != null;
    }

    @Override
    public boolean isReadable() {
        return this.input != null;
    }
}
