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

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.fields.SelectField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTSelectFieldRenderer extends AbstractGWTRenderer<SelectField> {

    @Override
    public Widget render(SelectField renderable) {
        ListBox object = new ListBox();
        this.buildAttributes(object, renderable);
        object.setName( renderable.getName() );

        int index = 0;
        Map<String, Object> options = ( (Map<String, Object>) renderable.getOptions());
        for ( Map.Entry<String, Object> entry : options.entrySet() ) {
            object.addItem( entry.getKey(),
                    entry.getValue() != null ?
                            String.valueOf(entry.getValue()) : "" );

            if ( renderable.getValues().contains(entry.getValue()) ) {
                object.setSelectedIndex(index++);
            }
        }

        return object;
    }
}
