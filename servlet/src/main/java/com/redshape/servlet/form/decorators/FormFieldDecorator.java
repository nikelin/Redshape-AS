package com.redshape.servlet.form.decorators;

import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.servlet.form.IFormField;
import com.redshape.servlet.form.IFormItem;
import com.redshape.utils.Commons;

public class FormFieldDecorator extends AbstractDecorator {
	public static final String LabelPlacement = "labelPlacement";
	
	protected void buildLabel( IFormItem item, StringBuilder builder ) {
		String label = ( (IFormField<?>) item ).getLabel();
		if ( label != null ) {
			builder.append("<label for=\"")
					.append( Commons.select( item.getName(), item.getId() ) )
					.append("\">")
					.append( StandardI18NFacade._( label ) )
					.append("</label>");
		}
	}
	
	protected void buildElement( String data, StringBuilder builder ) {
		builder.append("<span class=\"element\">").append( data ).append("</span>");
	}
	
	protected void build( IFormItem item, String data, StringBuilder builder ) {
		if ( !this.hasAttribute( LabelPlacement ) ) {
			this.buildLabel( item, builder);
			this.buildElement( data, builder);
		} else {
			switch ( this.<Placement>getAttribute( LabelPlacement ) ) {
			case BEFORE:
				this.buildLabel(item, builder);
				this.buildElement(data, builder);
			break;
			case AFTER:
				this.buildElement( data, builder );
				this.buildLabel( item, builder );
			break;
			}
		}
	}
	
	@Override
	public String decorate( IFormItem item, String data ) {
		if ( !( item instanceof IFormField ) ) {
			return data;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("<div class=\"field\">");
		this.build( item, data, builder );
		builder.append("</div>");
		
		return builder.toString();
	}
	
}
