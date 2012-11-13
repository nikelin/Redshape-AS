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

package com.redshape.io;


import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Entity which represents node on remote target file system
 *
 * @author nikelin
 */
public interface IFilesystemNode extends Closeable {

	/**
	 * Get name of current node (file, directory, etc.)
	 * @return
	 */
	public String getName();

	/**
	 * Get full path to current node
	 * @return
	 */
	public String getCanonicalPath();

	/**
	 * Check that this node still exists on a target files system
	 * @return
	 */
	public boolean isExists() throws IOException;

	/**
	 * Check that current node represents directory node
	 * @return
	 */
	public boolean isDirectory() throws IOException;

	/**
	 * Check that current node represents file node
	 * @return
	 */
	public boolean isFile() throws IOException;

	/**
	 *  Get first-level ancestors of current node ( only if <isDirectory() == true> )
	 * @return
	 */
	public String[] list() throws IOException;

	/**
	 * Returns parent for current node
	 * @return
	 */
	public String getParent() throws IOException;

	/**
	 * Remove node from files system
	 * @throws IOException
	 * @throws NetworkInteractionException
	 */
	public void remove() throws IOException;

	/**
	 * Returns read stream for current node ( only if <isLink() || isFile()> )
	 * @return
	 * @throws IOException
	 */
	public InputStream getInputStream() throws IOException;

	/**
	 *  Returns write stream for current node ( only if <isLink() || isFile()>)
	 * @return
	 * @throws IOException
	 */
	public OutputStream getOutputStream() throws IOException;

	/**
	 * Create node based on current node data on target files system
	 * @throws NetworkInteractionException
	 */
	public void createNew() throws IOException;

	public void mkdir() throws IOException;

}
