package com.vio.utils;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 22, 2010
 * Time: 10:23:11 AM
 * To change this template use File | Settings | File Templates.
 */
public class ResourcesLoader {
    private final static Logger log = Logger.getLogger( ResourcesLoader.class );
    final static Pattern NON_PRINTABLE = Pattern
            .compile("[^\t\n\r\u0020-\u007E\u0085\u00A0-\uD7FF\uE000-\uFFFC]");

    public File loadFile( String path ) throws IOException {
        return this.loadFile( path, true );
    }

    public File loadFile( String path, boolean searchPath ) throws IOException {
        File file = new File(path);
        if ( file.exists() ) {
            return file;
        }

        file = new File( Registry.getRootDirectory() + "/" + path);
        if ( searchPath && !file.exists() ) {
            file = this.find(path);
        }

        if ( file == null ) {
            throw new FileNotFoundException( path );
        }

        return file;
    }

    public String loadData( File file ) throws IOException {
        return this.loadData(file, false);
    }

    public String loadData( String path ) throws IOException {
        return this.loadData(  path, false );
    }

    public String loadData( String path, boolean escapeNonpritable ) throws IOException {
        return this.loadData( this.loadFile(path), escapeNonpritable );
    }

    public String loadData( File file, boolean escapeNonprintable ) throws IOException {
        FileInputStream stream = new FileInputStream(file);
        BufferedReader reader = new BufferedReader( new InputStreamReader(stream) );

        String result = new String();

        String line;
        while( null != ( line = reader.readLine() ) ) {
            result = result.concat(line) + "\n";
        }

        if ( escapeNonprintable ) {
            Matcher matcher = NON_PRINTABLE.matcher(result);
            if ( matcher.find() ) {
                result = matcher.replaceAll("\0");
            }
        }

        return result;
    }

    public InputStream loadResource( String path ) throws IOException {
        return new FileInputStream( this.loadFile(path) );
    }

    /**
     * @TODO загрузка из JAR-classpath элемента
     * @param path
     * @return
     * @throws FileNotFoundException
     */
    protected File find( String path ) throws FileNotFoundException {
        File candidateFile = null;
        
        for( String pathPart : System.getProperty("java.class.path").split(":") ) {
            candidateFile = new File( pathPart + File.separator + path );
            if ( !candidateFile.exists() ) {
                candidateFile = null;
            } else {
                break;
            }
        }

        return candidateFile;
    }

    public String[] getList( String path ) throws IOException {
        JarFile jarFile = new JarFile(path);
        Enumeration entries = jarFile.entries();
        List<String> targetEntries = new ArrayList<String>() ;
        while( entries.hasMoreElements() ) {
            JarEntry testing = (JarEntry) entries.nextElement();

            if ( testing.getName().endsWith(".class") )  {
                targetEntries.add( testing.getName() );
            }
        }

        return targetEntries.toArray( new String[targetEntries.size()] );
    }

    private void checkPrintable(CharSequence data) throws Exception {
        Matcher em = NON_PRINTABLE.matcher(data);
        if (em.find()) {
            throw new Exception(" special characters are not allowed");
        }
    }

}
