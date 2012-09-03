package com.redshape.renderer.forms.renderers.fields;

import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.fields.TextAreaField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTTextAreaFieldRenderer extends AbstractGWTRenderer<TextAreaField> {

    @Override
    public Widget render(TextAreaField renderable) {
        TextArea object = new TextArea();
        this.buildAttributes(object, renderable);
        object.setText( renderable.getValue() );
        object.setName( renderable.getName() );
        return object;
    }
}
