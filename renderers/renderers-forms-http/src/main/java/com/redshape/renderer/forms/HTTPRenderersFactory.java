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

package com.redshape.renderer.forms;

import com.redshape.form.IForm;
import com.redshape.form.fields.*;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.forms.renderers.StandardFormRenderer;
import com.redshape.renderer.forms.renderers.fields.*;

import java.lang.reflect.Constructor;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms
 * @date 2/17/12 {2:01 PM}
 */
public class HTTPRenderersFactory extends AbstractRenderersFactory {
    private static final String FACTORY_ID = "Renderers.Forms.HTTP";

    public HTTPRenderersFactory() {
        super();

        this.init();
    }

    @Override
    public String getFactoryId() {
        return FACTORY_ID;
    }

    @Override
    public <T, V> IRenderer<T,V> forEntity( Class<T> clazz ) {
        Class<? extends IRenderer<T, V>> rendererClazz = null;
        for ( Class<?> entityClazz : this.entities.keySet() ) {
            if ( entityClazz.isAssignableFrom(clazz) ) {
                rendererClazz = (Class<? extends IRenderer<T, V>>) this.entities.get(entityClazz);
                break;
            }
        }
    
        if ( rendererClazz == null ) {
            return null;
        }
    
        IRenderer<T, V> renderer = (IRenderer<T, V>) this.renderers.get(rendererClazz);
        if ( renderer != null ) {
            return renderer;
        }
    
        try {
            Constructor<? extends IRenderer<T, V>> bindConstructor = null;
            for ( Constructor<?> constructor : rendererClazz.getConstructors() ) {
                if ( constructor.getParameterTypes().length == 1
                        && constructor.getParameterTypes()[0].isAssignableFrom(IRenderersFactory.class) ) {
                    bindConstructor = (Constructor<? extends IRenderer<T, V>>) constructor;
                    break;
                }
            }
    
            if ( bindConstructor != null ) {
                renderer = bindConstructor.newInstance(this);
            } else {
                renderer = rendererClazz.newInstance();
            }
        } catch ( Throwable e ) {
            throw new InstantiationError(e.getMessage());
        }
    
        this.renderers.put( rendererClazz, renderer );
    
        return renderer;
    }


    protected void init() {
        this.addRenderer(IForm.class, StandardFormRenderer.class );
        this.addRenderer(CheckboxField.class, CheckboxFieldRenderer.class);
        this.addRenderer(InputField.class, InputFieldRenderer.class);
        this.addRenderer(TextAreaField.class, TextAreaRenderer.class );
        this.addRenderer(SelectField.class, SelectFieldRenderer.class );
        this.addRenderer(CheckboxGroupField.class, CheckboxGroupFieldRenderer.class );
        this.addRenderer(RadioGroupField.class, RadioGroupFieldRenderer.class );
        this.addRenderer(LabelField.class, LabelFieldRenderer.class);
    }
}
