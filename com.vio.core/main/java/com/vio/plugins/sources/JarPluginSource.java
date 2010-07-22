package com.vio.plugins.sources;

import com.vio.plugins.info.InfoFile;
import com.vio.utils.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 6:07:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class JarPluginSource implements PluginSource {
    private File file;
    private JarFile jarFile;

    public JarPluginSource( File file ) throws IOException {
        this.file = file;
        this.jarFile = new JarFile( file );
    }

    public String getPath() {
        return this.file.getAbsolutePath();
    }

    public InfoFile getInfoFile() throws IOException {
       String infoFileExtension = new String();
       JarEntry infoFileEntry = null;
       Enumeration<JarEntry> entries = this.jarFile.entries();
       for( JarEntry entry = entries.nextElement(); entries.hasMoreElements(); entry = entries.nextElement() ) {
            if ( entry.getName().contains("META-INF/info.xml") ) {
                infoFileExtension = StringUtils.getFileExtension( entry.getName() );
                infoFileEntry = entry;
                break;
            }
       }

       if ( infoFileEntry == null ) {
            throw new FileNotFoundException();
       }

       InputStream infoFileStream = this.jarFile.getInputStream( infoFileEntry );
       InfoFile file = new InfoFile( infoFileExtension, infoFileStream );

       return file;
    }
}
