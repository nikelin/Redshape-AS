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
