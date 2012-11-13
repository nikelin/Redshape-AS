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

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.fields.CheckboxField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTCheckboxFieldRenderer extends AbstractGWTRenderer<CheckboxField> {

    @Override
    public Widget render(CheckboxField renderable) {
        CheckBox object = new CheckBox();
        this.buildAttributes(object, renderable);
        object.setName( renderable.getCanonicalName() );
        object.setText( renderable.getLabel() );

        this.applyDecorators(renderable, object);

        return object;
    }
}
