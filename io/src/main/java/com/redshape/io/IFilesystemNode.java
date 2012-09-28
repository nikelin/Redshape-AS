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
