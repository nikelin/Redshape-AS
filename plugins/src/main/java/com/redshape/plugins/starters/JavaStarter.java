package com.redshape.plugins.starters;

import com.redshape.plugins.IPlugin;
import com.redshape.plugins.PluginInitException;
import com.redshape.plugins.meta.IPluginInfo;
import com.redshape.plugins.packagers.IPackageEntry;

import java.io.FilePermission;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.*;
import java.security.cert.Certificate;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.starters
 * @date 10/11/11 9:05 PM
 */
public class JavaStarter implements IStarterEngine {
	@Override
	public void start(IPluginInfo plugin) {
        try {
            final PermissionCollection permissions = new Permissions();
            for ( Permission permission : plugin.getPermissions() ) {
                permissions.add( permission );
            }
            
            URL[] urls = new URL[ plugin.getPackageDescriptor().getEntriesCount() ];
            ProtectionDomain[] protectionDomains = new ProtectionDomain[urls.length];
            int i = 0;
            for (IPackageEntry entry : plugin.getPackageDescriptor().getEntries() ) {
                urls[i++] = new URL( "file://" + entry.getPath() );
                protectionDomains[--i] = new ProtectionDomain( new CodeSource(urls[i], (Certificate[]) null), permissions );
            }

            ClassLoader loader = new URLClassLoader( urls ) {
                @Override
                protected PermissionCollection getPermissions(CodeSource codesource) {
                    return permissions;
                }
            };

            Class<?> clazz;
            try {
                clazz = loader.loadClass( plugin.getEntryPoint() );
            } catch ( Throwable e ) {
                throw new IllegalStateException("Unable to load entry point class");
            }
            
            if ( IPlugin.class.isAssignableFrom(clazz) ) {
                throw new IllegalStateException("Entry point class must be instance of " + IPlugin.class.getCanonicalName() );
            }

            IPlugin pluginInstance;
            try {
                 pluginInstance = (IPlugin) clazz.newInstance();
            } catch ( Throwable e ) {
                throw new IllegalStateException("Entry point class instantiation failed!", e );
            }
            
            pluginInstance.init();
        } catch ( MalformedURLException e ) {
            throw new IllegalStateException("Malformed path provided", e);
        } catch ( PluginInitException e ) {
            throw new IllegalStateException("Plugin initializing failed", e );
        }
	}

}
