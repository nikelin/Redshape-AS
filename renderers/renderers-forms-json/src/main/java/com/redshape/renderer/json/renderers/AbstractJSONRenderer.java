package com.redshape.renderer.json.renderers;

import com.redshape.renderer.IRenderer;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {3:10 PM}
 */
public abstract class AbstractJSONRenderer<T> implements IRenderer<T, String> {

    protected String createObject( String... fields ) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        int i = 0;
        for ( String field : fields ) {
            builder.append(field);
            
            if ( i++ != fields.length - 1 ) {
                builder.append(",");
            }
        }

        builder.append("}");

        return builder.toString();
    }
    
    protected String createObject( Map<String, Object> fields ) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        
        int i = 0;
        for ( Map.Entry<String, Object> field : fields.entrySet() ) {
            builder.append( this.createField(field.getKey(), field.getValue() ) );
            
            if ( i++ != fields.size() ) {
                builder.append(",");
            }
        }

        builder.append("}");

        return builder.toString();
    }

    protected String createList( Object... list ) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        
        int i = 0;
        for ( Object value : list ) {
            builder.append( String.valueOf(value));
            
            if ( i++ != list.length - 1 ) {
                builder.append(",");
            }
        }

        builder.append("]");

        return builder.toString();
    }

    protected String createField( String name, Object value ) {
        return "\"" + name + "\"" + ":" + String.valueOf(value);
    }

}
