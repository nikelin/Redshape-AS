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

package com.redshape.renderer.forms.renderers;

import com.redshape.form.IForm;
import com.redshape.form.IFormField;
import com.redshape.form.IFormItem;
import com.redshape.form.RenderMode;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.forms.renderers.fields.AbstractFormItemRenderer;
import com.redshape.utils.Commons;

public class StandardFormRenderer extends AbstractFormItemRenderer<IForm>
                                  implements IRenderer<IForm, String> {

    public StandardFormRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public String render(IForm renderable)  {
        return this.render(renderable, RenderMode.FULL);
    }

    @Override
    public void repaint(IForm renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public String render(IForm form, RenderMode mode) {
		StringBuilder builder = new StringBuilder();

		if ( mode.equals( RenderMode.FULL ) ) {
			if ( form.getContext() == null ) {
				builder.append("<form ")
					   .append("action=\"").append(
							   Commons.select( form.getAction() , "/" ) ).append("\" ")
					   .append("method=\"").append(
							   Commons.select( form.getMethod(), "POST" ) ).append("\" ");


				if ( form.getId() != null ) {
				   builder.append("id=\"")
						  .append( form.getId() )
						  .append("\"");
				}

				this.buildAttributes( builder, form );

				builder.append(">").append("\n\r");
			}
		}
		
		for ( IFormItem item : form.getItems() ) {
			if ( this.isAllowed( item, mode ) ) {
				builder.append( this.getRenderersFactory()
                        .<IFormItem, String>forEntity(item)
                        .render(item) )
                   .append("\n\r");
			}
		}

		if ( mode.equals( RenderMode.FULL ) ) {
			if ( form.getContext() == null ) {
				builder.append("</form>").append("\n\r");
			}
		}

        return this.applyDecorators(form, builder, mode );
	}
	
	protected boolean isAllowed( IFormItem item, RenderMode mode ) {
		switch ( mode ) {
		case FIELDS:
			if ( item instanceof IForm ) {
				return false;
			}
		case SUBFORMS:
			if ( item instanceof IFormField ) {
				return false;
			}
        case FULL:
        default:
            return true;
		}
	}

}
