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

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.Placement;
import com.redshape.utils.Commons;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ErrorsDecorator extends AbstractDecorator {
	public static final String PLACEMENT = "Attribute.PLACEMENT";

	public static class Attributes extends DecoratorAttribute {

		protected Attributes(String name) {
			super(name);
		}

		public static final Attributes Placement = new Attributes("DecoratorAttributes.Errors.Placement");
	}

	protected void buildList( Collection<String> messages, StringBuilder builder ) {
		boolean first = true;
		builder.append("<div class=\"errors\">");
		for ( String message : messages ) {
			if ( first ) {
				builder.append("<ul class=\"errors-list\">");
				first = false;
			}

			builder.append("<li>")
			   .append("<span class=\"error\">")
			   .append(message)
			   .append("</span>")
			   .append("</li>");
		}
		
		if ( !first ) {
			builder.append("</ul><div class=\"clear\">&nbsp;</div>");
		}
		builder.append("</div>");
	}

	protected Collection<String> buildMessagesList( IFormItem item ) {
		if ( item instanceof IForm ) {
			return this.buildMessagesList( (IForm) item );
		} else if ( item instanceof IFormField ) {
			return this.buildMessagesList( (IFormField<?>) item );
		} else {
			return new ArrayList<String>();
		}
	}

	protected Collection<String> buildMessagesList( IFormField<?> field ) {
		List<String> messages = new ArrayList<String>();
		for ( IValidationResult result : field.getValidationResults() ) {
			if ( !result.isValid() ) {
				messages.add( result.getMessage() );
			}
		}

		return messages;
	}

	protected Collection<String> buildMessagesList( IForm form ) {
		return form.getMessages();
	}

	@Override
	public String decorate(IFormItem item, String data) {
		Collection<String> messages = this.buildMessagesList(item);
		if ( messages.isEmpty() ) {
			return data;
		}

		StringBuilder builder = new StringBuilder();

		Placement placement = Commons.select( this.<Placement>getAttribute( Attributes.Placement ),
											  Placement.AFTER );
		switch (placement) {
		case BEFORE:
			this.buildList( messages, builder );
			builder.append(data);
		break;
		case AFTER:
		case WRAPPED:
		default:
			builder.append( data );
			this.buildList( messages, builder );
		break;
		}
			
		return builder.toString();	
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return Attributes.class.isAssignableFrom(attribute.getClass());
	}
}
