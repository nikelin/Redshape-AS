package com.redshape.renderer.forms.renderers.fields;

import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.fields.CheckboxGroupField;
import com.redshape.renderer.forms.renderers.AbstractGWTRenderer;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers.fields
 * @date 2/17/12 {4:03 PM}
 */
public class GWTCheckboxGroupFieldRenderer extends AbstractGWTRenderer<CheckboxGroupField> {

    @Override
    public Widget render(CheckboxGroupField renderable) {
        VerticalPanel panel = new VerticalPanel();
        Map<String, Object> options = (Map<String, Object>) renderable.getOptions();
        for (Map.Entry<String, Object> entry : options.entrySet() ) {
            panel.add( this.buildButton(renderable, entry) );
        }

        this.applyDecorators( renderable, panel );

        return panel;
    }
    
    protected Widget buildButton( CheckboxGroupField<?> renderable, Map.Entry<String, ?> entry ) {
        CheckBox button = new CheckBox();
        button.setName( renderable.getName() );
        button.setText( entry.getValue() == null ? "" : String.valueOf(entry.getValue()) );

        if ( renderable.getValues().contains( entry.getValue() ) ) {
            button.setValue(true);
        }

        this.buildAttributes(button, renderable);

        return button;
    }
}
