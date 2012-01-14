package com.redshape.plugins.starters;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.PluginInitException;
import com.redshape.plugins.meta.IPluginInfo;
import com.redshape.plugins.packagers.IPackageEntry;
import com.redshape.utils.ReflectionUtils;

import java.io.File;
import java.io.FilePermission;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.*;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.starters
 * @date 10/11/11 9:05 PM
 */
public class JavaStarter implements IStarterEngine {

    public class ByteClassLoader extends URLClassLoader {
        private final Map<String, byte[]> extraClassDefs;
        private PermissionCollection permissions;

        public ByteClassLoader(URL[] urls, ClassLoader parent,
                            Map<String, byte[]> extraClassDefs,
                            PermissionCollection permissions) {
            super(urls, parent);
            this.permissions = permissions;
            this.extraClassDefs = new HashMap<String, byte[]>(extraClassDefs);
        }

        @Override
        protected PermissionCollection getPermissions(CodeSource codesource) {
            if ( this.isEntryExists(codesource.getLocation().getPath() ) ) {
                return this.permissions;
            }

            return this.getClass().getProtectionDomain().getPermissions();
        }

        protected boolean isEntryExists( String path ) {
            String preparedPath = this.preparePath(path);
            for ( String key : this.extraClassDefs.keySet() ) {
                if ( key.endsWith( preparedPath ) ) {
                    return true;
                }
            }

            return false;
        }
        
        protected String preparePath( String path ) {
            return path.replaceAll(Pattern.quote("."), "\\" + File.separator ) + ".class";
        }
        
        protected byte[] findEntryByName( String name ) {
            String preparedName = this.preparePath(name);
            for ( String entryName : this.extraClassDefs.keySet() ) {
                if ( entryName.endsWith(preparedName) ) {
                    return this.extraClassDefs.get(entryName);
                }
            }

            return null;
        }

        @Override
        protected Class<?> findClass(final String name) throws ClassNotFoundException {
            byte[] classBytes = this.findEntryByName(name);
            if (classBytes != null) {
                return defineClass(name, classBytes, 0, classBytes.length);
            }

            return ClassLoader.getSystemClassLoader().loadClass(name);
        }
    }
    
    protected String prepareClassName( URI baseURI, String path ) {
        if ( !path.endsWith(".class") ) {
            return null;
        }
        
        return path.replace( baseURI.getPath(), "" );
    }

	@Override
	public void start(IPluginInfo plugin) {
        try {
            final PermissionCollection permissions = new Permissions();
            for ( Permission permission : plugin.getPermissions() ) {
                permissions.add( permission );
            }

            URL[] urls = new URL[ plugin.getPackageDescriptor().getEntriesCount() ];
            Map<String, byte[]> codeSources = new HashMap<String, byte[]>();
            for (IPackageEntry entry : plugin.getPackageDescriptor().getEntries() ) {
                String classEntryName = this.prepareClassName(entry.getDescriptor().getURI(), entry.getPath() );
                if ( classEntryName == null ) {
                    continue;
                }

                codeSources.put( classEntryName, entry.getData() );
            }

            ClassLoader loader = new ByteClassLoader( urls, null,
                    codeSources, permissions );

            Class<?> clazz;
            try {
                clazz = loader.loadClass( plugin.getEntryPoint() );
            } catch ( Throwable e ) {
                throw new IllegalStateException("Unable to load entry point class");
            }

            boolean found = false;
            for ( Class<?> interfaceClass : clazz.getInterfaces() ) {
                if ( interfaceClass.equals( IPlugin.class ) ) {
                    found = true;
                    break;
                }
            }

            if ( !found ) {
                throw new IllegalStateException("Plugin must implements "
                        + IPlugin.class.getCanonicalName() + " interface!");
            }

            IPlugin pluginInstance;
            try {
                 pluginInstance = (IPlugin) clazz.newInstance();
            } catch ( Throwable e ) {
                throw new IllegalStateException("Entry point class instantiation failed!", e );
            }
            
            pluginInstance.init();
        } catch ( PluginInitException e ) {
            throw new IllegalStateException("Plugin initializing failed", e );
        }
	}

}
