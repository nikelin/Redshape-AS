package com.redshape.form.builders;

import com.redshape.form.decorators.builders.IDecoratorBuilder;

public interface IBuildersFacade {

	public IDecoratorBuilder decoratorBuilder();
	
	public IFormBuilder formBuilder();
	
	public IFormFieldBuilder formFieldBuilder();
	
}
