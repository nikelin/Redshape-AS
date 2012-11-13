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
import com.redshape.form.fields.TextAreaField;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.Commons;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public class TextAreaRenderer extends AbstractFormFieldRenderer<TextAreaField> {

    public TextAreaRenderer(IRenderersFactory renderersFactory) {
        super(renderersFactory);
    }

    @Override
    public String render(TextAreaField item, RenderMode mode) {
        StringBuilder builder = new StringBuilder();
        builder.append("<textarea ")
               .append("name=\"").append(Commons.select( item.getCanonicalName(), item.getId() ) ).append("\" ")
               .append("id=\"").append(Commons.select( item.getId(), item.getName() ) ).append("\" ");

		this.applyErrorStateIfNeeds(item);
        this.buildAttributes(builder, item);

        builder.append(">");

        if ( item.getValue() != null ) {
            builder.append( item.getValue() );
        }

        builder.append("</textarea>");

        return this.applyDecorators( item, builder, mode );
    }

}