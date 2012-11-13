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
import com.redshape.persistence.migration.components.Table;
import com.redshape.persistence.migration.components.TableOption;
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
@TargetEntity( entity = Table.class )
public class CreateTableRenderer extends MySQLRenderer<Table> {

    public CreateTableRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    public String render( Table table ) {
        StringBuilder builder = new StringBuilder();
        builder.append( "create table " );
        builder.append( this.escapeField( table.getName() ) );
        builder.append( "(" );
        builder.append( this.renderFields(table) );
        builder.append( ")" );
        builder.append( this.renderOptions(table) );

        return builder.toString();
    }
    
    protected String renderFields( Table table ) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for ( Field field : table.getFields() ) {
            builder.append(
                    this.getRenderersFactory()
                            .forEntity(Field.class)
                            .render(field)
            );

            if ( i++ != table.getFields().size() - 1 ) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }
    
    protected String renderOptions( Table table ) {
        StringBuilder builder = new StringBuilder();

        int i = 0;
        for ( TableOption option : table.getOptions() ) {
            builder.append(
                    this.getRenderersFactory()
                            .forEntity(TableOption.class)
                            .render(option));

            if ( i++ != table.getOptions().size() - 1 ) {
                builder.append(" ");
            }
        }

        return builder.toString();
    }

}
