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

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.fields.RadioGroupField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTRadioGroupFieldRenderer extends AbstractGWTRenderer<RadioGroupField> {

    @Override
    public Widget render(RadioGroupField renderable) {
        VerticalPanel group = new VerticalPanel();
        Map<String, Object> options = (Map<String, Object>) renderable.getOptions();
        for (Map.Entry<String, Object> entry : options.entrySet() ) {
            group.add( this.buildButton(renderable, entry) );
        }

        this.applyDecorators(renderable, group);

        return group;
    }
    
    protected Widget buildButton( RadioGroupField<?> renderable, final Map.Entry<String, ?> entry ) {
        RadioButton button = new RadioButton( renderable.getName(),
                new SafeHtml() {
                    @Override
                    public String asString() {
                        return entry.getKey();
                    }
                });

        button.setText(entry.getValue() == null ? "" : String.valueOf(entry.getValue()));
        this.buildAttributes(button, renderable);
        return button;
    }
}
