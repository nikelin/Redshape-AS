package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.TableOption;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.render.TargetEntity;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
@TargetEntity( entity = TableOption.class )
public class TableOptionRenderer extends MySQLRenderer<TableOption> {

    public String render( TableOption option ) {
        StringBuilder builder = new StringBuilder();

        String tableOption = option.getOption().name();
        switch( option.getOption() ) {
            case DATA_DIRECTORY:
            case DEFAULT_COLLATE:
            case DEFAULT_CHARACTER_SET:
            case INDEX_DIRECTORY:
                builder.append( tableOption.replace('_', ' ') );
            break;
            default:
                builder.append( tableOption );
        }

        if ( option.getValue() != null ) {
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
