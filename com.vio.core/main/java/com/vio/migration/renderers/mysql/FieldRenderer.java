package com.vio.migration.renderers.mysql;

import com.vio.migration.components.Field;
import com.vio.migration.components.FieldOption;
import com.vio.migration.components.FieldType;
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
@TargetEntity( entity = Field.class )
public class FieldRenderer extends MySQLRenderer<Field> {

    public String render( Field field ) throws RendererException {
        try {
            RenderersFactory factory = RenderersFactory.getFactory(MySQLRenderersFactory.class);
            StringBuilder builder = new StringBuilder();
            builder.append( this.escapeField( field.getName() ) )
                   .append(" ")
                   .append( factory.forEntity(FieldType.class).render( field.getType() ) )
                   .append(" ")
                   .append( factory.forEntity(FieldOption.class).render( field.getOptions() ) );
            
            return builder.toString();
        } catch ( Throwable e ) {
            throw new RendererException();
        }
    }

}
