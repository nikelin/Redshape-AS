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

package com.redshape.net.connection.capabilities;

import com.redshape.net.connection.ConnectionException;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author nikelin
 * @date 16:06
 */
public interface IFilesystemCapabilitySupport extends IServerCapabilitySupport {

    public interface Node {

        public String getName();

        public OutputStream getOutputStream();
        
        public InputStream getInputStream();
        
        public boolean isFile() throws ConnectionException;

        public boolean isDirectory() throws ConnectionException;

        public boolean isExists() throws ConnectionException;

        public String getCanonicalPath();
        
        public void createNew() throws ConnectionException;
        
        public String[] list() throws ConnectionException;
        
        public String getParent();

        public void delete() throws ConnectionException;

        public void mkdir() throws ConnectionException;
        
    }
    
    public String getSeparator();
    
    public char getSeparatorChar();
    
    public Node getRoot() throws ConnectionException;
    
    public Node createFile( String path ) throws ConnectionException;

    public Node findFile(String name) throws ConnectionException;
    
}
