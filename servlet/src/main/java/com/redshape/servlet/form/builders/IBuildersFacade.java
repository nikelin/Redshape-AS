package com.redshape.servlet.form.builders;

import com.redshape.servlet.form.decorators.builders.IDecoratorBuilder;

public interface IBuildersFacade {

	public IDecoratorBuilder decoratorBuilder();
	
	public IFormBuilder formBuilder();
	
	public IFormFieldBuilder formFieldBuilder();
	
}
