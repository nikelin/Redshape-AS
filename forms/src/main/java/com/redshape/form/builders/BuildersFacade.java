package com.redshape.form.builders;

import com.redshape.form.builders.impl.StandardFormBuilder;
import com.redshape.form.builders.impl.StandardFormFieldBuilder;

public class BuildersFacade implements IBuildersFacade {
    private static IBuildersFacade instance = new BuildersFacade();

    public static void instance( IBuildersFacade facade ) {
        BuildersFacade.instance = facade;
    }

    public static IBuildersFacade instance() {
        return instance;
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
