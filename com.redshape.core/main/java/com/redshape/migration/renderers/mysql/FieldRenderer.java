package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.Field;
import com.redshape.migration.components.FieldOption;
import com.redshape.migration.components.FieldType;
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
@TargetEntity( entity = Field.class )
public class FieldRenderer extends MySQLRenderer<Field> {

    public String render( Field field ) throws RendererException {
        try {
            IRenderersFactory factory = AbstractRenderersFactory.getFactory(MySQLRenderersFactory.class);
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
