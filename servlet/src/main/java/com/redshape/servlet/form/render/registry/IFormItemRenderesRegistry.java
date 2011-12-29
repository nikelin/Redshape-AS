package com.redshape.servlet.form.render.registry;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.render.IFormFieldRenderer;

import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/29/11
 * Time: 11:27 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IFormItemRenderesRegistry {
    
    public <T extends IFormField<?>> void register( Class<T> fieldClazz, IFormFieldRenderer<T> renderer );
    
    public <T extends IFormField<?>> IFormFieldRenderer<T> getRenderer( Class<T> fieldClazz );

    public Collection<IFormFieldRenderer<?>> list();
    
}
