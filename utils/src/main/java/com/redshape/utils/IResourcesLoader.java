package com.redshape.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

	public File loadFile( String path, boolean searchPath ) throws IOException;

	public String loadData( File file ) throws IOException;

	public String loadData( String path ) throws IOException;

	public String loadData( String path, boolean escapeNonpritable ) throws IOException;

	public String loadData( File file, boolean escapeNonprintable ) throws IOException;

	public InputStream loadResource( String path ) throws IOException;

	public String[] getList( String path ) throws IOException;

	public void setRootDirectory( String rootDirectory );

	public String getRootDirectory();

}
