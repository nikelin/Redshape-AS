package com.redshape.servlet.form.builders;

import com.redshape.servlet.form.decorators.DecoratorAttribute;
import com.redshape.servlet.form.decorators.IDecorator;

import java.util.Collection;
import java.util.Map;

public interface IFormItemBuilder {
	
	/**
	 * Set new name to a form item which will be created
	 * as a builder result.
	 * If item is sub form, given name will be it's context identifier.
	 * 
	 * @param name
	 * @return
	 */
	public IFormItemBuilder withName( String name );
	
	public IFormItemBuilder withId( String id );
	
	/**
	 * Remove all binded decorators
	 * @return
	 */
	public IFormItemBuilder withEmptyDecorators();
	
	/**
	 * Add decorator to the item build profile. Decorator
	 * needs to affect rendering output without changing renderer
	 * internal behavior.
	 * 
	 * @see com.redshape.servlet.form.decorators.TagDecorator
	 * @param decorator
	 * @return
	 */
	public IFormItemBuilder withDecorator( IDecorator decorator );

	public IFormItemBuilder withDecoratorAttribute( DecoratorAttribute name, Object value );

	public IFormItemBuilder withDecoratorAttributes( Map<DecoratorAttribute, Object> attributes );

	/**
	 * Needs to add decorators collection in a batch manner.
	 * 
	 * @param decorators
	 * @return
	 */
	public IFormItemBuilder withDecorators( Collection<IDecorator> decorators );
	
	public IFormItemBuilder withAttribute( String name, Object value );
	
	/**
	 * Change current builder context to a IFormBuilder context.
	 * Needs in a case when type hidden by superclass methods invocation
	 * when returned `this` pointer reference to parent context.
	 * 
	 * @exception java.lang.UnsupportedOperationException Will be throwed if current builder
	 * haven't IFormBuilder as a superclass context.
	 * @return
	 */
	public IFormBuilder asFormBuilder();
	
	/**
	 * Change current builder context to a IFormFieldBuilder context.
	 * Needs in a case when type hidden by superclass methods invocation
	 * when returned `this` pointer reference to parent context.
	 * 
	 * @exception java.lang.UnsupportedOperationException Will be throwed if current builder
	 * haven't IFormFieldBuilder as a superclass context.
	 * @return
	 */
	public IFormFieldBuilder asFieldBuilder();
	
}
