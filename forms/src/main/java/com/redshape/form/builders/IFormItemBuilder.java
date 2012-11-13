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

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;

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
	 * @see com.redshape.form.decorators.TagDecorator
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
