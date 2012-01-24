package com.redshape.migration.renderers;

import com.redshape.renderer.IRenderer;
import com.redshape.renderer.RendererException;

import java.util.Collection;
import java.util.regex.Pattern;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers
 * @date Apr 6, 2010
 */
public abstract class MySQLRenderer<T> implements IRenderer<T, String> {
    private static final char ESCAPE_CHAR = '`';
    private final static Pattern NON_ESCAPABLE = Pattern.compile("[`() ,]");

    protected String escapeField( String name ) {
        return isEscapingNeeds( name ) ? ESCAPE_CHAR + name + ESCAPE_CHAR : name;
    }

    @Override
    public String render( Collection<T> renderables ) throws RendererException {
        StringBuilder builder = new StringBuilder();

        int num = 0;
        for ( T renderable : renderables ) {
            builder.append( this.render(renderable) );

            if ( ( num = ++num ) < renderables.size() ) {
                builder.append( this.getCollectionSeparator() );
            }
        }

        return builder.toString();
    }

    protected String getCollectionSeparator() {
        return ",";
    }

    private static boolean isEscapingNeeds( String value ) {
        return !value.isEmpty()
                || (
                    !( NON_ESCAPABLE.matcher( String.valueOf( value.charAt(0) ) ).find()
                        && NON_ESCAPABLE.matcher( String.valueOf( value.charAt( value.length() - 1 ) ) ).find() ) );
    }
}
