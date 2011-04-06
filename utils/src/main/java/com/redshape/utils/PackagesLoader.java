package com.redshape.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
* Created by IntelliJ IDEA.
* User: nikelin
* Date: Feb 22, 2010
* Time: 12:47:32 PM
* To change this template use File | Settings | File Templates.
*/
public class PackagesLoader {
    private static final Logger log = Logger.getLogger( PackagesLoader.class );
    private ResourcesLoader resourcesLoader;

    public void setResourcesLoader( ResourcesLoader loader ) {
        this.resourcesLoader = loader;
    }

    public ResourcesLoader getResourcesLoader() {
        return this.resourcesLoader;
    }

    public <T> Class<T>[] getClasses( String pkgName ) throws PackageLoaderException {
        return this.getClasses( pkgName, new InterfacesFilter( new Class[] {} ) );
    }

    @SuppressWarnings("unchecked")
	public <T> Class<T>[] getClasses( String pkgName, IFilter<?> filter ) throws PackageLoaderException {
        Set<Class<T>> classes = new HashSet<Class<T>>();
        for ( String path : System.getProperty("java.class.path").split(":") ) {
            try {
                Class<T>[] collectionPart = this.getClasses( path, pkgName, filter );
                if ( collectionPart != null ) {
                    classes.addAll( Arrays.asList( collectionPart ) );
                }
            } catch ( PackageLoaderException e ) {
                continue;
            }
        }

        return classes.toArray(new Class[classes.size()]);
    }

    public <T> Class<T>[] getClasses( String path, String pkgName ) throws PackageLoaderException {
        return this.getClasses(path, pkgName, new InterfacesFilter(new Class[]{}));
    }

    public <T> Class<T>[] getClasses( String path, String pkgName, IFilter filter ) throws PackageLoaderException {
        if ( path.endsWith(".jar") ) {
            return this.getClassesFromJar( path, pkgName, filter );
        } else {
            return this.getClassesFromIdle( path, pkgName, filter );
        }
    }

    protected <T> Class<T>[] getClassesFromJar( String path, String pkgName, IFilter filter ) throws PackageLoaderException {
        try {
            String folderName = this.convertToFolderName( pkgName );

            JarFile file = new JarFile( path );
            Enumeration<JarEntry> entries = file.entries();
            List<URL> targetEntries = new ArrayList<URL>() ;
            while( entries.hasMoreElements() ) {
                JarEntry testing = (JarEntry) entries.nextElement();

                if ( testing.getName().contains(folderName) &&
                        !testing.isDirectory() ) {
                    if ( testing.getName().endsWith(".class") ) {
                        targetEntries.add( new URL("jar", path + "!/", testing.getName() ) );
                    }
                }
            }

            List<Class<T>> result = new ArrayList<Class<T>>();
            URLClassLoader loader = new URLClassLoader( targetEntries.toArray( new URL[targetEntries.size()]) );
            for ( URL classUrl : targetEntries ) {
                String clsName = ( classUrl.getPath()
                                        .substring( 0 , classUrl.getPath().lastIndexOf(".") ) )
                                        .replaceAll(".class", "")
                                        .replaceAll( "/", "." );
                Class<T> clazz;
                try {
                    clazz = (Class<T>) loader.loadClass( clsName );
                    if ( filter == null || filter.filter(clazz) ) {
                        result.add(clazz);
                    }
                } catch ( Throwable e ) {
                    log.info( "Unable to load class: " + clsName, e );
                }
            }

            return result.toArray( new Class[result.size()] );
        } catch ( Throwable e ) {
            throw new PackageLoaderException( e.getMessage() );
        }
    }

    protected <T> Class<T>[] getClassesFromIdle( String path, String pkgName, IFilter filter ) throws PackageLoaderException {
        try {
            List<Class<T>> classes = new ArrayList<Class<T>>();
            String folderName = this.convertToFolderName( pkgName);

            File folder = resourcesLoader.loadFile(path + "/" + folderName);
            if ( folder == null || !folder.exists() || !folder.canRead() || ( !folder.isDirectory() && !folder.getPath().endsWith(".jar") ) ) {
                return null;
            }

            if ( folder.getPath().endsWith(".jar") ) {
                classes.addAll( (List) Arrays.asList( this.getClassesFromJar( path, pkgName, filter ) ) );
            } else {
                classes.addAll( this.<T>getClassesFromIdleFolder( path + File.separator + pkgName.replace(".", "/"), pkgName, filter ) );
            }

            return classes.toArray( new Class[ classes.size() ] );
        } catch ( PackageLoaderException e ) {
            throw e;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new PackageLoaderException( e.getMessage() );
        }
    }

    protected <T> Collection<Class<T>> getClassesFromIdleFolder( String folder, String pkgName, IFilter filter )
                                                                        throws ClassNotFoundException {
        Collection<Class<T>> classes = new HashSet<Class<T>>();
        File folderFile = new File( folder );

        String[] clsEntries = folderFile.list();
        for ( int i = 0; i < clsEntries.length; i++ ) {
            File clsFile = new File( folder + File.separator + clsEntries[i] );

            if ( clsFile.isDirectory() ) {
                try {
                    classes.addAll( this.<T>getClassesFromIdleFolder( folder + File.separator + clsEntries[i], pkgName + "." + clsEntries[i], filter ) );
                } finally {
                    continue;
                }
            }

            String className = clsEntries[i].substring( 0, clsEntries[i].indexOf(".") );

            Class<T> clazz = (Class<T>) ClassLoader.getSystemClassLoader().loadClass( pkgName + "." + className );
            if ( filter != null && !filter.filter(clazz) ) {
                continue;
            }

            classes.add( clazz );
        }

        return classes;
    }

    private String convertToFolderName( String packageName ) {
        return packageName.replace(".", File.separator );
    }

}


