package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.Table;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.RendererException;
import com.redshape.renderer.TargetEntity;

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

    public DropTableRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    public String render( Table table ) {
        StringBuilder builder = new StringBuilder();
        builder.append("drop table ");
        builder.append( table.getName() );
        builder.append(";");

        return builder.toString();
    }

}
