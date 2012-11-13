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

import com.redshape.form.IFormField;
import com.redshape.form.RenderMode;
import com.redshape.form.fields.RadioGroupField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Map;

/**
 * @package com.redshape.renderer.forms.impl.fields
 * @user cyril
 * @date 7/12/11 7:31 PM
 */
public class RadioGroupFieldRenderer extends AbstractFormFieldRenderer<RadioGroupField> {

    public RadioGroupFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    protected void renderItem( StringBuilder builder, IFormField<?> field, String name, Object value ) {
        builder.append("<label>")
                .append(name)
                .append("</label>");

        builder.append("<input type=\"radio\" value=\"")
                .append(value)
                .append("\" ");

        if ( field.getValue() != null && field.getValue().equals( value ) ) {
            builder.append(" checked=\"checked\" ");
        }

        builder.append("name=\"").append( field.getCanonicalName() ).append("\" ");

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes( builder, field );


        builder.append("/><br/>");
    }

    @Override
    public String render(RadioGroupField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        Map<String, Object> options = (Map<String, Object>) item.getOptions();
        for ( String option : options.keySet() ) {
            this.renderItem( builder, item, option, options.get(option) );
        }

        return this.applyDecorators(item, builder, mode);
    }

}
