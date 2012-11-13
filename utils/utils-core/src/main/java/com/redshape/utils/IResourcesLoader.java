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

package com.redshape.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Nov 8, 2010
 * Time: 5:27:03 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IResourcesLoader {

	public void addSearchPath( String searchPath );

	public Collection<String> getSearchPath();

	public void setSearchPath( Collection<String> path );

	public File loadFile( String path ) throws IOException;
    
    public File loadFile( URI uri ) throws IOException;

	public File loadFile( String path, boolean searchPath ) throws IOException;

	public String loadData( String path ) throws IOException;

	public String loadData( String path, boolean escapeNonpritable ) throws IOException;

	public InputStream loadResource( String path ) throws IOException;

	public String[] getList( String path ) throws IOException;

	public void setRootDirectory( String rootDirectory );

	public String getRootDirectory();

}
