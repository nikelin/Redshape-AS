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

package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.RenderMode;
import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.form.fields.InputField;
import com.redshape.renderer.IRenderersFactory;

public class InputFieldRenderer extends AbstractFormFieldRenderer<InputField> {

    public InputFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(InputField field, RenderMode mode ) {
        StringBuilder builder = new StringBuilder();
        builder.append("<input ")
                .append("type=\"").append( field.getType().name().toLowerCase() ).append("\" ");

        if ( field.getId() != null ) {
            builder.append("id=\"").append( field.getId() ).append( "\" ");
        }

        if ( field.getName() != null ) {
            builder.append("name=\"").append( field.getCanonicalName() ).append( "\" ");
        }


        if ( field.getValue() != null ) {
            String value = String.valueOf( field.getValue() );
            if ( field.getType().equals( InputField.Type.SUBMIT ) || field.getType().equals( InputField.Type.RESET ) ) {
                value = StandardI18NFacade._( value );
            }

            builder.append("value=\"").append( value ).append("\"");
        }

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes(builder, field);

        builder.append("/>");

        return this.applyDecorators(field, builder, mode);
    }


}
