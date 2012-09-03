package com.redshape.renderer.forms.renderers;

import com.google.gwt.core.client.GWT;
import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.fields.*;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.forms.renderers.fields.*;
import com.redshape.utils.Constants;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/17/12 {2:01 PM}
 */
public class GWTRenderersFactory extends AbstractRenderersFactory {
    public static int RESYNC_TIME = Constants.TIME_MILLISECOND * 100;

    private IRenderer<IForm, ?> standardRenderer = new StandardGWTFormRenderer();
    private InvalidationThread invalidationThread;
    
    public GWTRenderersFactory() {
        super();

        this.init();
        this.scheduleInvalidator();
    }

    protected void scheduleInvalidator() {
        this.invalidationThread = new InvalidationThread(this);
        this.invalidationThread.schedule(RESYNC_TIME);
    }
    
    @Override
    public String getFactoryId() {
        return "Renderers.Forms.GWT";
    }

    @Override
    public <T, V> IRenderer<T, V> forEntity(Class<T> object) {
        Class<? extends IRenderer<?, ?>> rendererClazz = this.entities.get(object);
        if ( rendererClazz == null ) {
            return null;
        }
        
        IRenderer<T, V> renderer = (IRenderer<T, V>) this.renderers.get( rendererClazz );
        if ( renderer instanceof StandardGWTFormRenderer ) {
            ((StandardGWTFormRenderer) renderer).setRenderersFactory(this);
        }

        return renderer;
    }

    protected void init() {
        this.addRenderer(IForm.class, StandardGWTFormRenderer.class );
        this.renderers.put(StandardGWTFormRenderer.class, new StandardGWTFormRenderer());
        this.addRenderer(CheckboxField.class, GWTCheckboxFieldRenderer.class );
        this.renderers.put( GWTCheckboxFieldRenderer.class, new GWTCheckboxFieldRenderer() );
        this.addRenderer(TextAreaField.class, GWTTextAreaFieldRenderer.class );
        this.renderers.put( GWTTextAreaFieldRenderer.class, new GWTTextAreaFieldRenderer() );
        this.addRenderer(InputField.class, GWTInputFieldRenderer.class );
        this.renderers.put( GWTInputFieldRenderer.class, new GWTInputFieldRenderer() );
        this.addRenderer(SelectField.class, GWTSelectFieldRenderer.class );
        this.renderers.put( GWTSelectFieldRenderer.class, new GWTSelectFieldRenderer() );
        this.addRenderer(RadioGroupField.class, GWTRadioGroupFieldRenderer.class );
        this.renderers.put( GWTRadioGroupFieldRenderer.class, new GWTRadioGroupFieldRenderer() );
        this.addRenderer(CheckboxGroupField.class, GWTCheckboxGroupFieldRenderer.class );
        this.renderers.put( GWTCheckboxFieldRenderer.class, new GWTCheckboxFieldRenderer() );
    }
}
