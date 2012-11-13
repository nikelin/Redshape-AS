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

package com.redshape.form.decorators;

import com.redshape.utils.IEnum;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form.decorators
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
