package com.redshape.renderer.forms.decorators;

import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.Placement;
import com.redshape.i18n.impl.StandardI18NFacade;
import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form.decorators
 * @date 8/26/11 2:44 PM
 */
public class LabelDecorator extends AbstractDecorator {

	@Override
	public String decorate(IFormItem item, String data) {
		StringBuilder builder = new StringBuilder();
		switch ( Commons.select(
				this.<Placement>getAttribute( DecoratorAttribute.Label.Placement ),
				Placement.BEFORE ) ) {
		case AFTER:
			builder.append( data );
			this.buildLabel( item, builder );
		break;
		case BEFORE:
		default:
			this.buildLabel(item, builder);
			builder.append( data );
		break;
		}

		return builder.toString();
	}

	protected String buildLabel( IFormItem item, StringBuilder builder ) {
		String label = ( (IFormField<?> ) item ).getLabel();
		if ( label != null ) {
			builder.append("<label for=\"")
					.append( Commons.select(item.getName(), item.getId()) )
					.append("\">")
					.append( StandardI18NFacade._(label) )
					.append("</label>");
		}

		return builder.toString();
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return DecoratorAttribute.Label.class.isAssignableFrom( attribute.getClass() );
	}
}
