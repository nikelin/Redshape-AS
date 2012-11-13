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
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.i18n.impl.StandardI18NFacade;

public class LegendDecorator extends AbstractDecorator {

	@Override
	public String decorate(IFormItem item, String data) {
		if ( ! ( item instanceof IForm ) ) {
			return data;
		}

		StringBuilder builder = new StringBuilder();
		builder.append("<fieldset ");

		if ( item instanceof IForm && ( (IForm) item ).getContext() != null ) {
			this.buildAttributes( item.getAttributes(), builder );
		}

	   	builder.append(">");
		builder.append("<legend>")
		       .append( StandardI18NFacade._( ( (IForm) item).getLegend() ) )
		       .append("</legend>");
		
		builder.append(data);
		
		builder.append("</fieldset>");
		
		return builder.toString();
	}

	@Override
	public boolean isSupported(DecoratorAttribute attribute) {
		return false;
	}
}
