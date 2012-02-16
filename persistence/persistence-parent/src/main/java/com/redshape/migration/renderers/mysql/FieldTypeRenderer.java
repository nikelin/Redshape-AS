package com.redshape.migration.renderers.mysql;

import com.redshape.migration.components.FieldType;
import com.redshape.migration.renderers.MySQLRenderer;
import com.redshape.migration.renderers.engine.MySQLRenderersFactory;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.RendererException;
import com.redshape.renderer.TargetEntity;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.migration.renderers.mysql
 * @date Apr 6, 2010
 */
@TargetEntity( entity = FieldType.class )
public class FieldTypeRenderer extends MySQLRenderer<FieldType> {

    public FieldTypeRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render( FieldType type ) {
        StringBuilder builder = new StringBuilder();
        builder.append( type.getType().name() )
               .append(" ");

        if ( type.getLength() != null && type.getDecimalLength() != null ) {
            builder.append( "(" )
                   .append( type.getLength() )
                   .append( "," )
                   .append( type.getDecimalLength() )
                   .append( ")" );
        } else if ( type.getLength() != null ) {
            builder.append( "(" )
                   .append( type.getLength() )
                   .append( ")" );
        }

        builder.append(" ");

        for ( String option : type.getOptions() ) {
            builder.append(option)
                   .append(" ");
        }

        return builder.toString();
    }

}
