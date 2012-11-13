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

import com.redshape.utils.Commons;
import com.redshape.utils.config.ConfigException;

import java.io.*;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/11/11
 * Time: 2:28 PM
 * To change this template use File | Settings | File Templates.
 */
public class FileSource implements IConfigSource {
    private File file;
    private OnChangeCallback onChangeCallback;
    private long initialMTime;

    public FileSource( File file ) {
        this(file, null);
    }

    public FileSource( File file, OnChangeCallback callback ) {
        Commons.checkNotNull(file);

        this.file = file;
        this.onChangeCallback = callback;

        this.markClean();
    }

    @Override
    public void setCallback(OnChangeCallback callback) {
        this.onChangeCallback = callback;
    }

    @Override
    public void markClean() {
        this.initialMTime = this.file.lastModified();
    }

    @Override
    public void reload() {
        this.onChangeCallback.onChanged();
    }

    @Override
    public boolean isChanged() {
        return this.file.lastModified() != this.initialMTime;
    }

    @Override
    public String read() throws ConfigException {
        try {
            InputStreamReader reader = new InputStreamReader( new FileInputStream(this.file) );
            StringBuilder builder = new StringBuilder();
            int read = 0;
            do {
                char[] data = new char[512];
                read = reader.read(data);

                if ( read > 0 ) {
                    builder.append( Arrays.copyOfRange( data, 0, read ) );
                }
            } while ( read > 0 );

            return builder.toString();
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
        }
    }

    @Override
    public void write( String data ) throws ConfigException {
        try {
            FileWriter writer = new FileWriter(this.file);
            writer.write(data);
        } catch ( IOException e ) {
            throw new ConfigException( e.getMessage(), e );
        }
    }

    @Override
    public boolean isWritable() {
        return this.file.canWrite();
    }

    @Override
    public boolean isReadable() {
        return this.file.canRead();
    }
}
