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

package com.redshape.renderer.forms.renderers.decorators;

import com.google.gwt.user.client.ui.CaptionPanel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IForm;
import com.redshape.form.IFormItem;

public class LegendDecorator extends AbstractDecorator {

	@Override
	public Widget decorate(IFormItem item, Widget data) {
		if ( ! ( item instanceof IForm ) ) {
			return data;
		}

        CaptionPanel wrapper = new CaptionPanel();

		if ( item instanceof IForm && ( (IForm) item ).getContext() != null ) {
			this.buildAttributes( item.getAttributes(), wrapper );
		}

        wrapper.setCaptionText( ( (IForm) item).getLegend() );
		wrapper.add(data);
		
		return wrapper;
	}
}
