package com.redshape.renderer.forms.renderers;

import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.forms.renderers.decorators.ErrorsDecorator;
import com.redshape.renderer.forms.renderers.decorators.FormFieldDecorator;
import com.redshape.renderer.forms.renderers.decorators.LabelDecorator;
import com.redshape.renderer.forms.renderers.decorators.LegendDecorator;
import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/17/12 {3:42 PM}
 */
public class StandardGWTFormRenderer extends AbstractGWTRenderer<IForm> {

    private IRenderersFactory renderersFactory;

    public StandardGWTFormRenderer() {
        this(null);
    }

    protected StandardGWTFormRenderer( IRenderersFactory renderersFactory ) {
        super();

        this.renderersFactory = renderersFactory;
    }

    public void setRenderersFactory( IRenderersFactory renderersFactory ) {
        this.renderersFactory = renderersFactory;
    }

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }
    
    @Override
    public Widget render(IForm renderable) {
        Commons.checkNotNull(renderable);

        FormPanel form = new FormPanel();
        this.buildAttributes(form, renderable);
        this.checkDecorators(renderable);
        form.setAction(Commons.select(renderable.getAction(), "#" ));
        form.setMethod(Commons.select(renderable.getMethod(), "POST" ));

        VerticalPanel panel = new VerticalPanel();
        form.add(panel);
        panel.setTitle(renderable.getLegend());
        
        for (IFormField<?> field : renderable.getFields() ) {
            this.checkDecorators(field);
            panel.add( this.getRenderersFactory().<IFormField, Widget>forEntity(field).render(field) );
        }

    
        this.applyDecorators(renderable, form );

        return form;
    }

    protected void checkDecorators( IFormItem field ) {
        if ( field instanceof IFormField && !field.hasDecorator(LabelDecorator.class) ) {
            field.setDecorator( new LabelDecorator() );
        }

        if ( field instanceof IForm && !field.hasDecorator(LegendDecorator.class) ) {
            field.setDecorator( new LegendDecorator() );
        }

        if ( !field.hasDecorator(ErrorsDecorator.class) ) {
            field.setDecorator( new ErrorsDecorator() );
        }

        if ( field instanceof IFormField && !field.hasDecorator( FormFieldDecorator.class ) ) {
            field.setDecorator( new FormFieldDecorator() );
        }
    }

}
