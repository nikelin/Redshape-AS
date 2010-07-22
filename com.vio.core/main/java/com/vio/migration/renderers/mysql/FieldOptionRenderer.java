package com.vio.migration.renderers.mysql;

import com.vio.migration.components.FieldOption;
import com.vio.migration.renderers.MySQLRenderer;
import com.vio.render.RendererException;
import com.vio.render.TargetEntity;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
@TargetEntity( entity = FieldOption.class )
public class FieldOptionRenderer extends MySQLRenderer<FieldOption> {

    public String render( FieldOption option ) throws RendererException {
        StringBuilder builder = new StringBuilder();

        String fieldOption = option.getOption().name();
        switch( option.getOption() ) {
            case NOT_NULL:
            case PRIMARY_KEY:
            case UNIQUE_KEY:
                builder.append( fieldOption.replace('_', ' ') );
            break;
            default:
                builder.append( fieldOption );
            break;
        }

        builder.append(" ");

        if ( null != option.getValue() ) {
            builder.append(" = ")
                   .append( option.getValue() );
        }

        return builder.toString();
    }

    @Override
    protected String getCollectionSeparator() {
        return " ";
    }

}
