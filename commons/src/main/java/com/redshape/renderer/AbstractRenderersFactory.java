package com.redshape.renderer;

import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public abstract class AbstractRenderersFactory implements IRenderersFactory {
    private static final Logger log = Logger.getLogger( AbstractRenderersFactory.class );

    private static Map<Class<? extends IRenderersFactory>,  IRenderersFactory> factories = new HashMap<
												   												Class<? extends IRenderersFactory>, 
												   												IRenderersFactory
												   										     >();

    private Map<Class<?>, Class<? extends IRenderer> > entities = new HashMap< 
																		Class<?>, 
	    			 													Class<? extends IRenderer>
																>();
    
    private Map<Class<? extends IRenderer>, IRenderer> renderers = new HashMap< 
    																	Class<? extends IRenderer>, 
    																	IRenderer
																	>();
    
    @Autowired( required = true )
    private ResourcesLoader resourcesLoader;
    
    @Autowired( required = true )
    private PackagesLoader packagesLoader;
    
    @Autowired( required = true )
    private IConfig config;

    public AbstractRenderersFactory( Class<? extends IRenderersFactory> self ) {
        this.initRenderers();
    }
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }
    
    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.resourcesLoader = loader;
    }
    
    public ResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
    }
    
    public PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }

    public static <T extends IRenderersFactory> T getFactory( Class<T> factoryClass )
                                                                    throws InstantiationException {
        T factory = (T) factories.get( factoryClass );
        if ( factory != null ) {
            return factory;
        }

        try {
        	factories.put( factoryClass, factory = factoryClass.newInstance() );
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        return factory;
    }

    @Override
    public <T extends IRenderer> T getRenderer( Class<T> clazz ) throws RendererException {
        T renderer = (T) this.renderers.get(clazz);
        if ( renderer != null ) {
            return renderer;
        }

        try {
            renderer = clazz.newInstance();
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            throw new RendererException();
        }

        this.addRenderer( clazz, renderer );

        return renderer;
    }

    @Override
    public void addRenderer( Class<? extends IRenderer> rendererClazz, IRenderer renderer ) {
        this.renderers.put( rendererClazz, renderer );
    }

    @Override
    public IRenderer forEntity( Object object ) throws RendererException {
        return this.forEntity( object.getClass() );
    }

	@Override
    public IRenderer forEntity( Class<?> object ) throws RendererException {
        return this.getRenderer( this.getForEntity(object) );
	}

    protected Class<? extends IRenderer> getForEntity( Class<?> object ) throws RendererException{
        try {
            Class<? extends IRenderer> rendererClass = this.entities.get(object);
            if ( rendererClass != null ) {
                return rendererClass;
            }

            for( Class<?> clazz : this.entities.keySet() ) {
                if ( clazz.isAssignableFrom( object ) ) {
                    rendererClass = this.entities.get( clazz );
                    break;
                }
            }

            if ( rendererClass != null ) {
               return rendererClass;
            }

            log.info( object.getCanonicalName() );
            throw  new RendererException();
		} catch ( Throwable e ) {
            log.error( e.getMessage(), e );
			throw new RendererException( e.getMessage(), e );
		}
	}

    protected Class<? extends IRenderer>[] getRenderersClasses( String id ) throws PackageLoaderException {
        try {
            Set<Class<? extends IRenderer>> renderers = new HashSet();

            String[] packages = this.getConfig()
                                        .get("settings")
                                        .get("renderers")
                                        .get( this.getFactoryId() )
                                        .get("packages").list("package");
            for ( String pkgPath : packages ) {
                renderers.addAll( Arrays.<Class<? extends IRenderer>>asList( 
                		this.getPackagesLoader()
                			.<IRenderer>getClasses( pkgPath , 
                					new InterfacesFilter( 
            							new Class[] { IRenderer.class }, 
            							new Class[] { TargetEntity.class }, 
            							true  
            						) 
                			) ) );   
            }

            return renderers.toArray( new Class[ renderers.size() ] );
        } catch ( Throwable e ) {
            throw new PackageLoaderException();
        }
    }

    private void initRenderers()  {
        try {
            log.info("Renderers initialization...");
            Class<? extends IRenderer>[] classes = this.getRenderersClasses( this.getFactoryId() );
            if ( classes.length == 0 ) {
                log.warn("No renderers was initialized for API interface!");
            }

            for ( Class<? extends IRenderer> rendererClass : classes ) {
                try {
                    TargetEntity target = rendererClass.getAnnotation( TargetEntity.class );
                    if ( target == null ) {
                        continue;
                    }

                    this.entities.put( target.entity(), rendererClass );
                    this.addRenderer( rendererClass, rendererClass.newInstance() );
                } catch ( Throwable e ) {
                    log.error( e.getMessage(), e );
                    log.error("Renderer " + rendererClass.getCanonicalName() + " initialization error...");
                }
            }
        } catch ( Throwable e ) {
        	log.error( e.getMessage(), e );
        }
    }

}
