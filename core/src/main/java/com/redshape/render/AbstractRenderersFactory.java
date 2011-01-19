package com.redshape.render;

import com.redshape.applications.Application;
import com.redshape.exceptions.ErrorCode;
import com.redshape.render.json.JSONRenderersFactory;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import java.util.*;

public abstract class AbstractRenderersFactory implements IRenderersFactory {
    private static final Logger log = Logger.getLogger( AbstractRenderersFactory.class );

    private static Map< Class<? extends IRenderersFactory>, IRenderersFactory> factories =
                                                                new HashMap< Class<? extends IRenderersFactory>, IRenderersFactory>();

    private Map< Class<? extends IRenderable>, Class<? extends IRenderer> > entities =
                                                                new HashMap< Class<? extends IRenderable>, Class<? extends IRenderer>>();
    
    private Map<Class<? extends IRenderer>, IRenderer> renderers = new HashMap<Class<? extends IRenderer>, IRenderer>();

    private static IRenderersFactory defaultInstance;
    static {
        try {
            defaultInstance = getFactory( JSONRenderersFactory.class );
        } catch ( Throwable e ) {
            log.error( "Cannot instantiate default factory", e );
            Application.exit();
        }
    }

    public AbstractRenderersFactory( Class<? extends IRenderersFactory> self ) {
        this.initRenderers();
    }

    public static <T extends AbstractRenderersFactory> T getFactory( Class<? extends IRenderersFactory> factoryClass )
                                                                    throws InstantiationException {
        IRenderersFactory factory = factories.get( factoryClass );
        if ( factory != null ) {
            return (T) factory;
        }

        try {
            factory = factoryClass.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        factories.put( factoryClass, factory );

        return (T) factory;
    }

    public static <T extends IRenderersFactory> T getDefault() {
        return (T) defaultInstance;
    }

    public static void setDefaultFactory( IRenderersFactory defaultFactory ) {
        defaultInstance = defaultFactory;
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
    public IRenderer forEntity( IRenderable object ) throws RendererException {
        return this.forEntity( object.getClass() );
    }

	@Override
    public IRenderer forEntity( Class<? extends IRenderable> object ) throws RendererException {
        return this.getRenderer( this.getForEntity(object) );
	}

    protected Class<? extends IRenderer> getForEntity( Class<? extends IRenderable> object ) throws RendererException{
        try {
            Class<? extends IRenderer> rendererClass = this.entities.get(object);
            if ( rendererClass != null ) {
                return rendererClass;
            }

            for( Class<? extends IRenderable> clazz : this.entities.keySet() ) {
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
			throw new RendererException( ErrorCode.EXCEPTION_INTERNAL );
		}
	}

    protected Class<? extends IRenderer>[] getRenderersClasses( String id ) throws PackageLoaderException {
        try {
            Set<Class<? extends IRenderer>> renderers = new HashSet();

            String[] packages = Registry.getConfig()
                                        .get("settings")
                                            .get("renderers")
                                                .get( this.getFactoryId() )
                                                    .get("packages").list("package");
            for ( String pkgPath : packages ) {
                renderers.addAll( Arrays.<Class<? extends IRenderer>>asList( Registry.getPackagesLoader().<IRenderer>getClasses( pkgPath , new InterfacesFilter( new Class[] { IRenderer.class }, new Class[] { TargetEntity.class }, true  ) ) ) );   
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
            Application.exit();
        }
    }

}
