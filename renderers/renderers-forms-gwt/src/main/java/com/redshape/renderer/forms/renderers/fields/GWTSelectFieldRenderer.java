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
