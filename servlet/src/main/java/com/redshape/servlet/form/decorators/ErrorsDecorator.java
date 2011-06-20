package com.redshape.servlet.form.decorators;

import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.validators.result.IValidationResult;

public class ErrorsDecorator extends AbstractDecorator {
	public static final String PLACEMENT = "Attribute.PLACEMENT";
	
	protected void buildMessage( IValidationResult result, StringBuilder builder ) {
		builder.append("<li>")
			   .append("<span class=\"error\">")
			   .append( result.getMessage() )
			   .append("</span>")
			   .append("</li>");
	}
	
	protected void buildList( IFormField<?> field, StringBuilder builder ) {
		boolean first = true;
		for ( IValidationResult result : field.getValidationResults() ) {
			if ( !result.isValid() ) {
				if ( first ) {
					builder.append("<ul class=\"errors-list\">");
					first = false;
				}
				
				this.buildMessage( result, builder );
			}
		}
		
		if ( !first ) {
			builder.append("</ul>");
		}
	}
	
	@Override
	public String decorate(IFormItem item, String data) {
		if ( ! (item instanceof IFormField) ) {
			return data;
		}
		
		final IFormField<?> field = (IFormField<?>) item;
		
		StringBuilder builder = new StringBuilder();
		Placement placement = this.getAttribute( PLACEMENT ) == null ? 
											Placement.AFTER :
											this.<Placement>getAttribute( PLACEMENT );
				
		switch (placement) {
		case AFTER:
		case WRAPPED:
			builder.append( data );
			this.buildList( field, builder );
		break;
		case BEFORE:
			this.buildList( field, builder );
			builder.append(data);
		break;
		}
			
		return builder.toString();	
	}	
	
}
