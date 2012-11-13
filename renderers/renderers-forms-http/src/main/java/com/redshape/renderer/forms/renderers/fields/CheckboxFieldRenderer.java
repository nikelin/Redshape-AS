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
import com.redshape.form.fields.CheckboxField;
import com.redshape.renderer.IRenderersFactory;

public class CheckboxFieldRenderer extends AbstractFormFieldRenderer<CheckboxField> {

    public CheckboxFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(CheckboxField field, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<input ")
                .append(" type=\"checkbox\" ");

        builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");

        if ( field.getId() != null ) {
            builder.append("id=\"").append( field.getId() ).append("\" ");
        }

        if ( field.getValue() != null ) {
            builder.append("checked=\"checked\"");
        }

        this.applyErrorStateIfNeeds(field);

        this.buildAttributes( builder, field );

        builder.append("/>");

        return this.applyDecorators(field, builder, mode);
    }

}
