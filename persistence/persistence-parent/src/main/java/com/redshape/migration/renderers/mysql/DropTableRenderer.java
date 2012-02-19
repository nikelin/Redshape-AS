package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.DroppingTable;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.renderer.IRenderersFactory;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
public class DropTableRenderer extends MySQLRenderer<DroppingTable> {

    public DropTableRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render( DroppingTable table ) {
        StringBuilder builder = new StringBuilder();
        builder.append("drop table ");
        builder.append( table.getName() );
        builder.append(";");

        return builder.toString();
    }

}
