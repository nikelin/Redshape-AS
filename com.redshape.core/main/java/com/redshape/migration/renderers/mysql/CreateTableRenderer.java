package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.Field;
import com.redshape.migration.components.Table;
import com.redshape.migration.components.TableOption;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.migration.renderers.engine.MySQLRenderersFactory;
import com.redshape.render.AbstractRenderersFactory;
import com.redshape.render.IRenderersFactory;
import com.redshape.render.RendererException;
import com.redshape.render.TargetEntity;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
@TargetEntity( entity = Table.class )
public class CreateTableRenderer extends MySQLRenderer<Table> {

    public String render( Table table ) throws RendererException {
        try {
            IRenderersFactory factory = AbstractRenderersFactory.getFactory( MySQLRenderersFactory.class);

            StringBuilder builder = new StringBuilder();
            builder.append( "create table " );
            builder.append( this.escapeField( table.getName() ) );
            builder.append( "(" );
            builder.append(
                factory.forEntity(Field.class)
                        .render(table.getFields() )
            );
            builder.append(")");
            builder.append(
                factory.forEntity(TableOption.class)
                    .render( table.getOptions() )
            );

            return builder.toString();
        } catch ( Throwable e ) {
            throw new RendererException();
        }
    }

}
