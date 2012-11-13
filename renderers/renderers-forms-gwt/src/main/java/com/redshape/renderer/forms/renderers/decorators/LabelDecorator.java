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

import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.decorators.DecoratorAttribute;
import com.redshape.form.decorators.Placement;
import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form.decorators
 * @date 8/26/11 2:44 PM
 */
public class LabelDecorator extends AbstractDecorator {

	@Override
	public Widget decorate(IFormItem item, Widget widget) {
        HorizontalPanel decorated = new HorizontalPanel();
		switch ( Commons.select(
				this.<Placement>getAttribute( DecoratorAttribute.Label.Placement ),
				Placement.BEFORE ) ) {
		case AFTER:
            decorated.add( decorated );
			this.buildLabel( item, decorated);
		break;
		case BEFORE:
		default:
			this.buildLabel(item, decorated);
            decorated.add(widget);
		break;
		}

        return decorated;
	}

	protected void buildLabel( IFormItem item, Panel widget ) {
		String label = ( (IFormField<?> ) item ).getLabel();
		if ( label == null ) {
            return;
        }

        Label labelUI = new Label();
        labelUI.setText( label );
        labelUI.getElement().setAttribute("for", Commons.select(item.getName(), item.getId()) );
        widget.add( labelUI );
	}
}
