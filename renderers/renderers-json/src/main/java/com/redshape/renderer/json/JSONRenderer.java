package com.redshape.renderer.json;

import com.redshape.renderer.Handler;
import com.redshape.renderer.IRenderer;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 15, 2010
 * Time: 12:06:13 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class JSONRenderer<T> implements IRenderer<T, String> {
    private final static char EOL_CHAR = '\n';
    private final static char ESCAPE_CHAR = '"';
    private final static Pattern NON_ESCAPABLE = Pattern.compile("[^{}\\[\\]\"']");


    private static Map<Class<?>, Method> renderers = new HashMap<Class<?>, Method>();

    public JSONRenderer() {
        super();

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

    @Handler( type = Throwable.class )
    public String renderValue( Throwable e ) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", e.getClass().getCanonicalName() );
        result.put("message", e.getMessage() );

        return this.renderValue( result );
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

    @Override
    public String render( T renderable ) {
        return this.renderValue( this.renderMap(renderable) );
    }

    abstract public Map<String, Object> renderMap( T object );

    protected void initRendererMethods() {
        for ( Method method : this.getClass().getMethods() ) {
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
