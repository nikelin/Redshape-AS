package com.vio.migration.renderers.mysql;

import com.vio.migration.components.Field;
import com.vio.migration.components.Table;
import com.vio.migration.components.TableOption;
import com.vio.migration.components.TableOptions;
import com.vio.migration.renderers.MySQLRenderer;
import com.vio.migration.renderers.engine.MySQLRenderersFactory;
import com.vio.render.RendererException;
import com.vio.render.RenderersFactory;
import com.vio.render.TargetEntity;

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
            RenderersFactory factory = RenderersFactory.getFactory( MySQLRenderersFactory.class);

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
