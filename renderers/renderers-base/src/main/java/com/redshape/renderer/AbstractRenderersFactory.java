package com.redshape.renderer;

import com.redshape.utils.*;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;

import java.util.*;

public abstract class AbstractRenderersFactory implements IRenderersFactory {
    private static final Logger log = Logger.getLogger( AbstractRenderersFactory.class );

    public static String PACKAGES_PATH = "settings.renderers.packages";

    private Map<Class<?>, Class<? extends IRenderer<?, ?>> > entities 
				= new HashMap<Class<?>, Class<? extends IRenderer<?,?>>>();
    
    private Map<Class<? extends IRenderer<?, ?>>, IRenderer<?, ?>> renderers 
				= new HashMap<Class<? extends IRenderer<?,?>>, IRenderer<?,?>>();

    private IResourcesLoader resourcesLoader;
    private IPackagesLoader packagesLoader;
    private IConfig config;

    protected AbstractRenderersFactory( IConfig config,
    									IPackagesLoader packagesLoader,
										IResourcesLoader resourcesLoader )
    	throws ConfigException {
    	super();
    		
		Commons.checkNotNull(config);
		Commons.checkNotNull(packagesLoader);
		Commons.checkNotNull(resourcesLoader);
    		
    	this.packagesLoader = packagesLoader;
    	this.resourcesLoader = resourcesLoader;
    	this.config = config;
    	
        this.initRenderers();
    }
    
    protected String[] getPackagesPath() throws ConfigException {
		return this.getConfig().get(PACKAGES_PATH).list();
    }
    
    protected IResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
    }
    
    protected IPackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    protected IConfig getConfig() {
    	return this.config;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T, V> IRenderer<T, V> getRenderer( Class<? extends IRenderer<T, V>> clazz ) {
		IRenderer<T, V> renderer = (IRenderer<T,V>) this.renderers.get(clazz);

        try {
            renderer = clazz.newInstance();
        } catch ( Throwable e ) {
            return null;
        }

        this.addRenderer( clazz, renderer );

        return renderer;
    }

    @Override
    public <T, V> void addRenderer( Class<? extends IRenderer<T, V>> rendererClazz, 
    								IRenderer<T, V> renderer ) {
        this.renderers.put( rendererClazz, renderer );
    }

    @Override
    public <T, V> IRenderer<T, V> forEntity( Object object ) {
        return this.<T, V>forEntity( object.getClass() );
    }

	@Override
    public <T, V> IRenderer<T, V> forEntity( Class<T> object ) {
        return this.<T, V>getRenderer( this.<T, V>getForEntity(object) );
	}

    @SuppressWarnings("unchecked")
	protected <T, V> Class<? extends IRenderer<T, V>> getForEntity( Class<T> object ) {
        Class<? extends IRenderer<T, V>> rendererClass =
                (Class<? extends IRenderer<T, V>>) this.entities.get(object);
        if ( rendererClass != null ) {
            return rendererClass;
        }

        for( Class<?> clazz : this.entities.keySet() ) {
            if ( clazz.isAssignableFrom( object ) ) {
                rendererClass = (Class<? extends IRenderer<T, V>>)
                        this.entities.get( clazz );
                break;
            }
        }

        if ( rendererClass != null ) {
           return rendererClass;
        }

        return null;
	}

    @SuppressWarnings("unchecked")
	protected Class<? extends IRenderer<?, ?>>[] getRenderersClasses( String id ) 
			throws PackageLoaderException, ConfigException {
        try {
            Set<Class<? extends IRenderer<?, ?>>> renderers = 
            		new HashSet<Class<? extends IRenderer<?, ?>>>();

            String[] packages = this.getPackagesPath();
            for ( String pkgPath : packages ) {
                renderers.addAll( Arrays.<Class<? extends IRenderer<?, ?>>>asList( 
                		this.getPackagesLoader()
                			.<IRenderer<?, ?>>getClasses( pkgPath , 
                					new InterfacesFilter<IRenderer<?,?>>(
            							new Class[] { IRenderer.class }, 
            							new Class[] { TargetEntity.class }, 
            							true  
            						) 
                			) ) );   
            }

            return renderers.toArray( new Class[ renderers.size() ] );
        } catch ( ConfigException e ) {
    		throw e;
        } catch ( PackageLoaderException e ) {
        	throw e;
        } catch ( Throwable e ) {
            throw new PackageLoaderException( e.getMessage(), e );
        }
    }

    @SuppressWarnings("unchecked")
	protected void initRenderers()  {
        try {
            Class<? extends IRenderer<?, ?>>[] classes = 
            		this.getRenderersClasses(this.getFactoryId() );
            if ( classes.length == 0 ) {
                return;
            }

            for ( Class<? extends IRenderer<?, ?>> rendererClass : classes ) {
                try {
                    TargetEntity target = rendererClass.getAnnotation(TargetEntity.class);
                    if ( target == null ) {
                        continue;
                    }

                    this.entities.put( target.entity(), rendererClass );
                    
                    this.addRenderer( (Class) rendererClass, rendererClass.newInstance() );
                } catch ( Throwable e ) {
                	log.error( e.getMessage(), e );
                	continue;
                }
            }
        } catch ( Throwable e ) {
        	throw new IllegalStateException( e.getMessage(), e );
        }
    }

}
