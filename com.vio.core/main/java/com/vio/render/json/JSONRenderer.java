package com.vio.render.json;

import com.vio.exceptions.ExceptionWithCode;
import com.vio.render.*;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 15, 2010
 * Time: 12:06:13 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class JSONRenderer<T extends Renderable> implements Renderer<T> {
	private final static char EOL_CHAR = '\n';
    private final static char ESCAPE_CHAR = '"';
    private final static Logger log = Logger.getLogger( JSONRenderer.class );
    private final static Pattern NON_ESCAPABLE = Pattern.compile("[^{}\\[\\]\"']");


    private static Map<Class<?>, Method> renderers = new HashMap<Class<?>, Method>();

    public JSONRenderer() {
        this.initRendererMethods();
    }

    public String escape( String value ) {
        return isEscapingNeeds(value) ?  ESCAPE_CHAR + value + ESCAPE_CHAR
                                      : value;
    }

    private static boolean isEscapingNeeds( String value ) {
        return !value.isEmpty()
                    && !( value.equals("null")
                        && NON_ESCAPABLE.matcher( String.valueOf( value.charAt(0) ) ).find()
                            && NON_ESCAPABLE.matcher( String.valueOf( value.charAt( value.length() - 1 ) ) ).find() );
    }

    @Handler( type = String.class )
    public String renderValue( String value ) {
    	return this.escape( value );
    }

    @Handler( type = Object[].class )
    public String renderValue( Object[] value ) {
    	return this.convertArrayToJSON( value );
    }

    @Handler( type = Map.class )
    public  String renderValue( Map value ) {
        return this.convertMapToJSON( value );
    }

    @Handler( type = Number.class )
    public String renderValue( Number number ) {
    	return this.renderValue( number.toString() );
    }

    @Handler( type = Collection.class )
    public String renderValue( Collection value ) {
    	return this.convertArrayToJSON( value.toArray() );
    }

    @Handler( type = ExceptionWithCode.class )
    public String renderValue( ExceptionWithCode e ) {
    	Map<String, Object> result = new HashMap<String, Object>();
    	result.put("code", e.getCode().name() );
    	result.put("message", e.getMessage() );

        return this.renderValue( result );
    }

    @Handler( type = Renderable.class )
    public String renderValue( Renderable value ) throws RendererException {
        try {
            log.info( value.getClass().getCanonicalName() );
            Renderer r = RenderersFactory.getDefault().forEntity(value);
            if ( r != null ) {
                return String.valueOf( r.render(value) );
            }

            return null;
        } catch ( Throwable e ) {
            log.info( e.getMessage(), e );
            return null;
        }
    }

    @Handler( type = Object.class )
    public String renderValue( Boolean value ) {
        return String.valueOf( value );
    }

    @Handler( type = Object.class )
    public String renderValue( Object value ) {
        String result = null;
        if ( value == null ) {
            return this.renderValue("");
        }

        try {
            for ( Class<?> clazz : renderers.keySet() ) {
                if ( clazz.isAssignableFrom( value.getClass() ) ) {
                    Method method = renderers.get(clazz);

                    // reject self-invokation
                    if ( !clazz.equals( Object.class ) ) {
                        result = String.valueOf( method.invoke( this, value ) );
                        break;
                    }
                }
            }
    	} catch ( Throwable e ) {
            result = this.renderValue( String.valueOf( value ) );
        }

        return result;
    }
    
    protected String convertArrayToJSON( Object[] list ) {
    	String result = "[";

        int i = 0;
    	for ( Object element : list ) {
    		result += this.renderValue(element);

            if ( i++ < list.length - 1 ) {
                result += ",";
            }
    	}
    	result += "]";
    	
    	return result;
    }
    
    protected String convertMapToJSON( Map map ) {
    	String result = "{";

        int i = 0;
    	for ( Object key : map.keySet() ) {
    		result += this.escape( key.toString() ) + " : " + this.renderValue( map.get(key) );

            if ( i++ <  map.keySet().size() - 1 ) {
                result += ",";    
            }
    	}
    	
    	result += "}";
    	
    	return result;
    }

	public String render( Collection<? extends T> objects ) throws RendererException {
		List<String> data = new ArrayList<String>();
		for( T object : objects ) {
			data.add( this.render( object ) ) ;
		}
		
		return this.convertArrayToJSON( data.toArray() );
	}

    public String render( T renderable ) throws RendererException {
        return this.renderValue( this.renderMap(renderable) );
    }

    public Map<String, Object> renderMap( T object ) throws RendererException {
        return new HashMap();
    }

    public static void initRendererMethods() {
        for ( Method method : JSONRenderer.class.getMethods() ) {
            Handler handlerMeta = method.getAnnotation( Handler.class );
            if ( handlerMeta == null ) {
                continue;
            }

            renderers.put( handlerMeta.type(), method );
        }
    }
    
    protected char getEOL() {
    	return EOL_CHAR;
    }


}