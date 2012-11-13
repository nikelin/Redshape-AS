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

package com.redshape.form.builders;

import com.redshape.form.builders.impl.StandardFormBuilder;
import com.redshape.form.builders.impl.StandardFormFieldBuilder;
import com.redshape.form.decorators.builders.IDecoratorBuilder;
import com.redshape.utils.Commons;

public class BuildersFacade implements IBuildersFacade {
    private static IBuildersFacade instance = new BuildersFacade();
    private static IDecoratorBuilder<?> decoratorsBuilder;
    
    public static void instance( IBuildersFacade facade ) {
        BuildersFacade.instance = facade;
    }

    public static IBuildersFacade instance() {
        return instance;
    }

    public static IDecoratorBuilder<?> setDecoratorsBuilder( IDecoratorBuilder<?> builder ) {
        Commons.checkNotNull(builder);

        decoratorsBuilder = builder;
        return builder;
    }
    
    public static <T> IDecoratorBuilder<T> newDecoratorBuilder() {
        if ( decoratorsBuilder == null ) {
            throw new IllegalStateException("Decorator builder class not provided");
        }
        
        try {
            return (IDecoratorBuilder<T>) decoratorsBuilder;
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }
    
    public static IFormBuilder newFormBuilder() {
        return instance().formBuilder();
    }

    public static IFormFieldBuilder newFieldBuilder() {
        return instance().formFieldBuilder();
    }

    @Override
    public IFormBuilder formBuilder() {
        return new StandardFormBuilder();
    }

    @Override
    public IFormFieldBuilder formFieldBuilder() {
        return new StandardFormFieldBuilder();
    }

}
