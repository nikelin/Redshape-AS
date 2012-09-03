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
