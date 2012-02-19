package com.redshape.renderer.forms.decorators;

import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.IDecorator;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractDecorator implements IDecorator<String> {
	private Map<DecoratorAttribute, Object> attributes;
	
	public AbstractDecorator() {
		this( new HashMap<DecoratorAttribute, Object>() );
	}
	
	public AbstractDecorator( Map<DecoratorAttribute, Object> attributes ) {
		this.attributes = attributes;
	}
	
	protected void buildAttributes( Map<String, Object> attributes, StringBuilder builder ) {
		for ( String key : attributes.keySet() ) {
			builder.append( key ).append("=\"").append( attributes.get(key) ).append("\"");
		}
	}

	@Override
	public boolean hasAttribute( DecoratorAttribute name ) {
		return this.attributes.containsKey(name);
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T getAttribute( DecoratorAttribute name ) {
		return (T) this.attributes.get(name);
	}

	@Override
	public void setAttribute( DecoratorAttribute name, Object value ) {
		this.attributes.put(name, value);
	}

	@Override
	public void setAttributes( Map<DecoratorAttribute, Object> attributes ) {
		this.attributes = attributes;
	}
}
