package com.redshape.servlet.form.decorators;

import com.redshape.utils.IEnum;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.decorators
 * @date 8/26/11 2:43 PM
 */
public class DecoratorAttribute implements IEnum<String> {
	private String name;

	protected DecoratorAttribute( String name ) {
		this.name = name;
	}

	public static class Label extends DecoratorAttribute {

		protected Label(String name) {
			super(name);
		}

		public static final Label Placement = new Label("DecoratorAttribute.Label.Placement");

	}

	@Override
	public String name() {
		return this.name;
	}
}
