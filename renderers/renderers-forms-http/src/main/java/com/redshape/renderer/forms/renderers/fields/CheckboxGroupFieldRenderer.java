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
import com.redshape.form.fields.CheckboxGroupField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Map;

/**
 * @package com.redshape.renderer.forms.impl.fields
 * @user cyril
 * @date 6/27/11 10:42 PM
 */
public class CheckboxGroupFieldRenderer extends AbstractFormFieldRenderer<CheckboxGroupField> {

    public CheckboxGroupFieldRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    protected void renderItem( StringBuilder builder, CheckboxGroupField<?> field, String name, Object value ) {
        builder.append("<span class=\"label\">").append( StandardI18NFacade._(name) ).append("</span>")
                .append("<input type=\"checkbox\" value=\"")
                .append(value)
                .append("\" ");

        if ( field.getValue() != null && field.getValues().contains(value) ) {
            builder.append(" checked=\"checked\" ");
        }

        this.applyErrorStateIfNeeds(field);
        this.buildAttributes( builder, field );

        String canonicalName = field.getCanonicalName();
        if ( !canonicalName.endsWith("[]") ) {
            canonicalName = canonicalName.concat("[]");
        }

        builder.append("name=\"").append( canonicalName ).append("\" ");

        builder.append("/><br/>");
    }

    @Override
    public String render(CheckboxGroupField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<fieldset>");
        Map<String, Object> options = (Map<String, Object>) item.getOptions();
        for ( String option : options.keySet() ) {
            this.renderItem( builder, item, option, options.get(option) );
        }

        if ( options.isEmpty() ) {
            builder.append("<strong>Any options available</strong>");
        }

        builder.append("</fieldset>");

        return this.applyDecorators(item, builder, mode);
    }

}
