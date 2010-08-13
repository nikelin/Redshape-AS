package com.vio.render;

import com.vio.exceptions.ErrorCode;
import com.vio.render.json.JSONRenderersFactory;
import com.vio.utils.PackageLoaderException;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public abstract class RenderersFactory {
    private static final Logger log = Logger.getLogger( RenderersFactory.class );
    private static RenderersFactory defaultInstance = new JSONRenderersFactory();
    private static Map< Class<? extends RenderersFactory>, RenderersFactory > factories =
                                                                new HashMap< Class<? extends RenderersFactory>, RenderersFactory>();
    private Map< Class<? extends IRenderable>, Class<? extends Renderer> > entities =
                                                                new HashMap< Class<? extends IRenderable>, Class<? extends Renderer>>();
    
    private Map<Class<? extends Renderer>, Renderer> renderers = new HashMap<Class<? extends Renderer>, Renderer>();

    public RenderersFactory() {
        this.initRenderers();
    }

    public static <T extends RenderersFactory> T getFactory( Class<? extends RenderersFactory> factoryClass )
                                                                    throws InstantiationException {
        RenderersFactory factory = factories.get( factoryClass );
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

    public static <T extends RenderersFactory> T getDefault() {
        return (T) defaultInstance;    
    }

    public static void setDefaultFactory( RenderersFactory defaultFactory ) {
        defaultInstance = defaultFactory;
    }

    public <T extends Renderer> T getRenderer( Class<T> clazz ) throws RendererException {
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

    public void addRenderer( Class<? extends Renderer> rendererClazz, Renderer renderer ) {
        this.renderers.put( rendererClazz, renderer );
    }

    public Renderer forEntity( IRenderable object ) throws RendererException {
        return this.forEntity( object.getClass() );
    }

	public Renderer forEntity( Class<? extends IRenderable> object ) throws RendererException {
        return this.getRenderer( this.getForEntity(object) );
	}

    protected Class<? extends Renderer> getForEntity( Class<? extends IRenderable> object ) throws RendererException{
        try {
            Class<? extends Renderer> rendererClass = this.entities.get(object);
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

    abstract protected Class<? extends Renderer>[] getRenderersClasses() throws PackageLoaderException;

    private void initRenderers()  {
        try {
            log.info("Renderers initialization...");
            Class<? extends Renderer>[] classes = this.getRenderersClasses();
            for ( Class<? extends Renderer> rendererClass : classes ) {
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
