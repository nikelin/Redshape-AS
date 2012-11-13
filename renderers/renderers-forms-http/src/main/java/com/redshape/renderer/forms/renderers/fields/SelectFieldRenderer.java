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
import com.redshape.form.fields.SelectField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Map;

public class SelectFieldRenderer extends AbstractFormFieldRenderer<SelectField> {

    public SelectFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(SelectField field, RenderMode mode ) {
        StringBuilder builder = new StringBuilder();
        builder.append("<select ");

        builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");


        if ( field.getId() != null ) {
            builder.append("id=\"").append( field.getId() ).append("\" ");
        }

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes(builder, field);

        builder.append(">");

        Map<String, ?> options = field.getOptions();
        for ( String key : options.keySet() ) {
            builder.append("<option value=\"").append( options.get(key) ).append("\" ");

            if ( field.getValue() != null && field.getValue().toString().equals( options.get( key ).toString() ) ) {
                builder.append(" selected=\"selected\" ");
            }

            builder.append(">");
            builder.append( StandardI18NFacade._( key ) )
                    .append("</option> ");
        }

        builder.append("</select>");

        return this.applyDecorators( field, builder, mode );
    }

}
