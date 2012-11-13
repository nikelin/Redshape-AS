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

package com.redshape.form.decorators.builders;

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;
import com.redshape.form.decorators.Placement;

import java.util.List;
import java.util.Map;

public interface IDecoratorBuilder<T> {

	public IDecoratorBuilder<T> withAttribute( DecoratorAttribute name, Object value );
	
	public IDecoratorBuilder<T> withAttributes( Map<DecoratorAttribute, Object> attributes );
	
	public IDecorator<T> withFormFieldDecorator();
	
	public IDecorator<T> withComposedDecorator( IDecorator<T>... decorators );
	
	public IDecorator<T> withComposedDecorator( List<IDecorator<T>> decorators );
	
	public IDecorator<T> withTagDecorator( String tagName, Placement placement );
	
	public IDecorator<T> withLegendDecorator();
	
}
