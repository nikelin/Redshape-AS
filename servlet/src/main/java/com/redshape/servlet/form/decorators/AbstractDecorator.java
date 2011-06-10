package com.redshape.servlet.form.decorators;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class AbstractDecorator implements IDecorator {
	private Map<String, Object> attributes;
	
	public AbstractDecorator() {
		this( new HashMap<String, Object>() );
	}
	
	public AbstractDecorator( Map<String, Object> attributes ) {
		this.attributes = attributes;
	}
	
	protected void buildAttributes( StringBuilder builder ) {
		for ( String key : this.getAttributeNames() ) {
			builder.append( key ).append("=\"").append( this.getAttribute(key) ).append("\"");
		}
	}
	
	protected Object getAttribute( String name ) {
		return this.attributes.get(name);
	}
	
	protected Set<String> getAttributeNames() {
		return this.attributes.keySet();
	}
	
	public void setAttribute( String name, Object value ) {
		this.attributes.put(name, value);
	}
	
	public void setAttributes( Map<String, Object> attributes ) {
		this.attributes = attributes;
	}
}
