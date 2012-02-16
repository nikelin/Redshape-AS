package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.Field;
import com.redshape.migration.components.FieldOption;
import com.redshape.migration.components.FieldType;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.TargetEntity;

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

    public FieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render( Field field ) {
        StringBuilder builder = new StringBuilder();
        builder.append( this.escapeField( field.getName() ) )
               .append(" ")
               .append(
                       this.getRenderersFactory().forEntity(FieldType.class).render( field.getType() ) )
               .append(" ");

        int i = 0;
        for ( FieldOption option : field.getOptions() ) {
            builder.append( this.getRenderersFactory().forEntity(FieldOption.class).render(option) );

            if ( i++ != field.getOptions().size() - 1 ) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
