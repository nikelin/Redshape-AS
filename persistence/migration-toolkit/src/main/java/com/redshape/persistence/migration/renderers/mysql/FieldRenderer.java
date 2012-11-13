/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.persistence.migration.renderers.mysql;

import com.redshape.persistence.migration.components.Field;
import com.redshape.persistence.migration.components.FieldOption;
import com.redshape.persistence.migration.components.FieldType;
import com.redshape.persistence.migration.renderers.MySQLRenderer;
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
