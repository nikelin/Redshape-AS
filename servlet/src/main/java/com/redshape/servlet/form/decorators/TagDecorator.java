package com.redshape.servlet.form.decorators;

import com.redshape.servlet.form.IFormItem;

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
