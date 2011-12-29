package com.redshape.servlet.form.render.registry;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.fields.*;
import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.servlet.form.render.impl.fields.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/29/11
 * Time: 11:28 AM
 * To change this template use File | Settings | File Templates.
 */
public class StandardFormItemRenderersRegistry implements IFormItemRenderesRegistry {
    private static IFormItemRenderesRegistry defaultInstance = new StandardFormItemRenderersRegistry();

    private Map<Class<? extends IFormField<?>>, IFormFieldRenderer<?>> registry = 
                    new HashMap<Class<? extends IFormField<?>>, IFormFieldRenderer<?>>();

    public StandardFormItemRenderersRegistry() {
        this.init();
    }
    
    protected void init() {
        this.register(CheckboxField.class, new CheckboxFieldRenderer() );
        this.register(InputField.class, new InputFieldRenderer() );
        this.register(RadioGroupField.class, new RadioGroupFieldRenderer() );
        this.register(CheckboxGroupField.class, new CheckboxGroupFieldRenderer() );
        this.register(SelectField.class, new SelectFieldRenderer() );
        this.register(LabelField.class, new LabelFieldRenderer() );
        this.register(TextAreaField.class, new TextAreaRenderer() );
    }
    
    public static void setDefault( IFormItemRenderesRegistry instance ) {
        defaultInstance = instance;
    }

    public static IFormItemRenderesRegistry getDefault() {
        return defaultInstance;
    }
    
    @Override
    public <T extends IFormField<?>> void register(Class<T> fieldClazz, IFormFieldRenderer<T> renderer) {
        this.registry.put( fieldClazz, renderer );
    }

    @Override
    public <T extends IFormField<?>> IFormFieldRenderer<T> getRenderer(Class<T> fieldClazz) {
        return (IFormFieldRenderer<T>) this.registry.get(fieldClazz);
    }

    @Override
    public Collection<IFormFieldRenderer<?>> list() {
        return this.registry.values();
    }
}
