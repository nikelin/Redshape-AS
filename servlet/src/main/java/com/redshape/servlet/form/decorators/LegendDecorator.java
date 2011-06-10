package com.redshape.servlet.form.decorators;

import com.redshape.servlet.form.IForm;
import com.redshape.servlet.form.IFormItem;

public class LegendDecorator extends AbstractDecorator {

	@Override
	public String decorate(IFormItem item, String data) {
		if ( ! ( item instanceof IForm ) ) {
			return data;
		}
		
		StringBuilder builder = new StringBuilder();
		builder.append("<fieldset ");
	    this.buildAttributes(builder);
	   	builder.append(">");
		builder.append("<legend>")
		       .append( ( (IForm) item).getLegend() )
		       .append("</legend>");
		
		builder.append(data);
		
		builder.append("</fieldset>");
		
		return builder.toString();
	}
	
}
