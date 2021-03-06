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

package com.redshape.renderer.forms.decorators;

import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.Placement;

import java.util.HashMap;
import java.util.Map;

public class TagDecorator extends AbstractDecorator {

	public static class Attributes extends DecoratorAttribute {

		protected Attributes(String name, boolean renderable ) {
			super(name);
		}

		public static final Attributes Attributes = new Attributes("Decorator.Tag.Attributes", true);

	}

	private String tagName;
	private Placement placement;
	
	public TagDecorator( String tagName, Placement placement ) {
		this(tagName, placement, new HashMap<DecoratorAttribute, Object>() );
	}
	
	public TagDecorator( String tagName, Placement placement, Map<DecoratorAttribute, Object> attributes ) {
		super( attributes );

		this.placement = placement;
		this.tagName = tagName;
	}
	
	protected void buildStart( StringBuilder builder, Map<String, Object> attributes,
							   boolean pair ) {
		if ( builder == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		builder.append("<")
		   .append( this.tagName )
		   .append(" ");
		this.buildAttributes(attributes, builder);

		if ( this.hasAttribute( Attributes.Attributes ) ) {
			this.buildAttributes( this.<Map<String,Object>>getAttribute( Attributes.Attributes ), builder );
		}

		builder.append( pair ? ">" : "/>");
	}
	
	protected void buildEnd( StringBuilder builder ) {
		builder.append("</" + this.tagName + ">");
	}
	
	@Override
	public String decorate(IFormItem item, String data) {
		StringBuilder builder = new StringBuilder();

		switch ( this.placement ) {
		case AFTER:
			builder.append(data);
			this.buildStart( builder, item.getAttributes(), false);
		break;
		case BEFORE:
			this.buildStart(builder, item.getAttributes(), false);
		break;
		case WRAPPED:
			this.buildStart(builder, item.getAttributes(), true);
			builder.append(data);
			this.buildEnd(builder);
		}
		
		return builder.toString();
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return Attributes.class.isAssignableFrom(attribute.getClass());
	}
}
