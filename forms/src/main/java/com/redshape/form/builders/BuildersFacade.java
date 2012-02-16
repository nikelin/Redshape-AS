package com.redshape.form.builders;

import com.redshape.form.builders.impl.StandardFormBuilder;
import com.redshape.form.builders.impl.StandardFormFieldBuilder;
import com.redshape.form.decorators.builders.IDecoratorBuilder;
import com.redshape.form.decorators.builders.impl.StandardDecoratorBuilder;

public class BuildersFacade implements IBuildersFacade {
	private static IBuildersFacade instance = new BuildersFacade();
	
	public static void instance( IBuildersFacade facade ) {
		BuildersFacade.instance = facade;
	}
	 
	public static IBuildersFacade instance() {
		return instance;
	}
	
	public static IDecoratorBuilder newDecoratorBuilder() {
		return instance().decoratorBuilder();
	}
	
	public static IFormBuilder newFormBuilder() {
		return instance().formBuilder();
	}
	
	public static IFormFieldBuilder newFieldBuilder() {
		return instance().formFieldBuilder();
	}

	@Override
	public IDecoratorBuilder decoratorBuilder() {
		return new StandardDecoratorBuilder();
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
