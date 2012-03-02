package com.redshape.renderer.forms.renderers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.forms.ClientProcessRequest;
import com.redshape.renderer.forms.renderers.decorators.ErrorsDecorator;
import com.redshape.renderer.forms.renderers.decorators.FormFieldDecorator;
import com.redshape.renderer.forms.renderers.decorators.LabelDecorator;
import com.redshape.renderer.forms.renderers.decorators.LegendDecorator;
import com.redshape.renderer.managers.RenderersManager;
import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/17/12 {3:42 PM}
 */
public class StandardGWTFormRenderer extends AbstractGWTRenderer<IForm> {

    private class SubmitHandler implements FormPanel.SubmitHandler {
        private IForm form;

        private SubmitHandler(IForm form) {
            this.form = form;
        }

        @Override
        public void onSubmit(FormPanel.SubmitEvent event) {
            try {
                this.form.process(new ClientProcessRequest(this.form));
            } catch ( Throwable e ) {
                GWT.log( e.getMessage(), e );
                StandardGWTFormRenderer.this.render(this.form);
            }
        }
    };

    private IRenderersFactory renderersFactory;

    public StandardGWTFormRenderer() {
        this(null);

        this.doResultsCache(false);
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
    public void repaint(IForm renderable) {
        Element element = renderable.getRawElement();
        if ( element == null ) {
            throw new IllegalStateException("Object has not been attached to then rendering context");
        }
        
        Widget repainted = this.render(renderable);
        RootPanel.get().add(repainted);
        repainted.getElement()
                 .getParentElement()
                    .replaceChild( repainted.getElement(), element );
    }

    @Override
    public Widget render(final IForm renderable) {
        Commons.checkNotNull(renderable);

        FormPanel form = (FormPanel) this.restoreCache(renderable.getClass());
        if ( form != null ) {
            return form;
        }

        form = new FormPanel();

        renderable.setRawElement(form.getElement());

        form.addSubmitHandler(new SubmitHandler(renderable));

        this.buildAttributes(form, renderable);
        this.checkDecorators(renderable);
        form.setAction(Commons.select(renderable.getAction(), "#" ));
        form.setMethod(Commons.select(renderable.getMethod(), "POST" ));

        VerticalPanel panel = new VerticalPanel();
        form.add(panel);
        panel.setTitle(renderable.getLegend());

        for (IFormField<?> field : renderable.getFields() ) {
            this.checkDecorators(field);
            
            if ( field.getRawElement() != null && RenderersManager.getInstance().isValid(field) ) {
                panel.add( (Widget) field.getRawElement() );
            } else {
                panel.add( this.createElement(field) );
            }
        }


        this.applyDecorators(renderable, form );

        renderable.makeClean();

        if ( this.isDoResultsCache() ) {
            this.saveCache(renderable.getClass(), form);
        }

        return form;
    }

    protected Widget createElement( IFormField<?> field ) {
        Widget widget = this.getRenderersFactory().<IFormField, Widget>forEntity(field).render(field);
        field.setRawElement( widget.getElement() );
        field.makeClean();
        return widget;
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
