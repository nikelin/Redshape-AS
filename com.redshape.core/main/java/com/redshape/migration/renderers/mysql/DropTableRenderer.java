package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.Table;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.render.RendererException;
import com.redshape.render.TargetEntity;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
@TargetEntity( entity = Table.class )
public class DropTableRenderer extends MySQLRenderer<Table> {

    public String render( Table table ) throws RendererException {
        StringBuilder builder = new StringBuilder();
        builder.append("drop table ");
        builder.append( table.getName() );
        builder.append(";");

        return builder.toString();
    }

    public String render( Collection<? extends Table> tables ) throws RendererException {
        StringBuilder builder = new StringBuilder();
        builder.append("drop table ");

        int num = 0;
        for ( Table table : tables ) {
            builder.append( table.getName() );

            if ( num < tables.size() )  {
                builder.append(",");
            }

            num++;
        }

        return builder.toString();
    }

}
