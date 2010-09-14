package com.redshape.plugins.sources;

import com.redshape.plugins.info.InfoFile;
import com.redshape.utils.Registry;
import com.redshape.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:48:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class DirectoryPluginSource implements PluginSource {
    private File directory;
    private InfoFile infoFile;

    public DirectoryPluginSource( String path ) {
        this( new File(path) );
    }

    public DirectoryPluginSource( File directory ) {
        this.directory = directory;
    }

    public String getPath() {
        return directory.getPath();
    }

    public InfoFile getInfoFile() throws IOException {
        InfoFile infoFile = this.infoFile;     
        if ( infoFile != null ) {
            return infoFile;
        }

        for ( String fItem : this.directory.list() ) {
            if ( fItem.equals("info.xml") ) {
                File fItemFile = Registry.getResourcesLoader().loadFile( this.directory.getAbsolutePath() + "/" + fItem );

                if ( fItemFile.canRead() ) {
                    infoFile = new InfoFile( StringUtils.getFileExtension(fItem), fItemFile );
                    break;
                }
            }
        }

        if ( infoFile == null ) {
            throw new FileNotFoundException();
        }

        return infoFile;
    }
}
