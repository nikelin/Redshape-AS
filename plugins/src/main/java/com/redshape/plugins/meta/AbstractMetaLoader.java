package com.redshape.plugins.meta;

import com.redshape.plugins.packagers.IPackageDescriptor;
import com.redshape.plugins.packagers.IPackageStarter;
import com.redshape.plugins.starters.EngineType;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;

import java.lang.reflect.Constructor;
import java.net.URI;
import java.security.Permission;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Nikelin
 * Date: 09.01.12
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMetaLoader implements IMetaLoader {
    private DateFormat format;

    protected AbstractMetaLoader(DateFormat format) {
        this.format = format;
    }

    protected IPluginInfo buildPluginInfo( IPackageDescriptor descriptor, IConfig config )
            throws ConfigException {
        PluginInfo info = new PluginInfo( descriptor );

        IConfig resourcesNode = config.get("resources");
        if ( !resourcesNode.isNull() ) {
            info.setResourcesPath(resourcesNode.value());
        }

        IConfig sourceNode = config.get("sources");
        if ( !sourceNode.isNull() ) {
            info.setSourcesPath(config.get("sources").value());
        }

        IConfig nameNode = config.get("name");
        if ( !nameNode.isNull() ) {
            info.setName( nameNode.value() );
        }
        
        info.setStarterInfo( this.parseStarterInfo(info, config) );
        info.setEntryPoint(config.get("mainClass").value());
        info.setPublisher(this.parsePublisher(info, config));
        info.setArchVersion( this.parseArchVersion(config) );

        for (Permission permission : this.parsePermissions(config) ) {
            info.addPermission(permission);
        }

        return info;
    }
    
    protected IPackageStarter parseStarterInfo( IPluginInfo info, IConfig config ) throws ConfigException {
        return info.createStarterInfo(
            EngineType.valueOf(config.get("starter.engine").value()),
            config.get("starter.version").value()
        );
    }

    protected List<Permission> parsePermissions( IConfig config ) throws ConfigException {
        List<Permission> permissions = new ArrayList<Permission>();
        for ( IConfig permissionNode : config.get("permissions").childs() ) {
            permissions.add( this.loadPermission(permissionNode) );
        }

        return permissions;
    }

    protected <T extends Permission> T loadPermission( IConfig permissionNode ) throws ConfigException {
        try {
            Class<T> permissionClass = (Class<T>) Class.forName(permissionNode.get("type").value() );

            List<IConfig> parameterNodes = permissionNode.get("parameters").childs();

            String[] permissionParameters = new String[parameterNodes.size()];
            int off = 0;
            for ( IConfig parameterNode : parameterNodes ) {
                permissionParameters[off++] = parameterNode.value();
            }

            Constructor<T> targetConstructor = null;
            for ( Constructor<?> constructor : permissionClass.getConstructors() ) {
                if ( constructor.getParameterTypes().length == permissionParameters.length ) {
                    targetConstructor = (Constructor<T>) constructor;
                    break;
                }
            }

            if ( targetConstructor == null ) {
                throw new ConfigException("Unable to find appropriate permission initializer");
            }

            return targetConstructor.newInstance( permissionParameters );
        } catch ( Throwable e ) {
            throw new ConfigException("Unknown permission class provided", e );
        }
    }

    protected IPublisherInfo parsePublisher( IPluginInfo descriptor,
                                             IConfig config ) throws ConfigException {
        try {
            IPublisherInfo info = descriptor.createPublisherInfo();
            info.setCompany( config.get("publisher.company").value() );
            info.setPublishedOn( this.format.parse( config.get("publisher.date").value() ) );
            info.setURI(URI.create(config.get("publisher.url").value()));

            return info;
        } catch ( ParseException e ) {
            throw new ConfigException("Failed to parse date value format", e );
        }
    }

    protected String parseArchVersion( IConfig config ) throws ConfigException {
        return config.get("archVersion").value();
    }

}
