package com.redshape.servlet.form.decorators;

import java.util.HashMap;
import java.util.Map;

import com.redshape.servlet.form.IFormItem;

public class TagDecorator extends AbstractDecorator {
	
	private String tagName;
	private Placement placement;
	
	public TagDecorator( String tagName, Placement placement ) {
		this(tagName, placement, new HashMap<String, Object>() );
	}
	
	public TagDecorator( String tagName, Placement placement, Map<String, Object> attributes ) {
		super(attributes);
		
		this.placement = placement;
		this.tagName = tagName;
	}
	
	protected void buildStart( StringBuilder builder, boolean pair ) {
		if ( builder == null ) {
			throw new IllegalArgumentException("<null>");
		}
		
		builder.append("<")
		   .append( this.tagName )
		   .append(" ");
		this.buildAttributes(builder);
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
			this.buildStart(builder, false);
		break;
		case BEFORE:
			this.buildStart(builder, false);
		break;
		case WRAPPED:
			this.buildStart(builder, true);
			builder.append(data);
			this.buildEnd(builder);
		}
		
		return builder.toString();
	}
	
}
