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

package com.redshape.renderer.forms.renderers.fields;

import com.redshape.form.IFormField;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 13.06.11
 * Time: 1:16
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFormFieldRenderer<T extends IFormField> extends AbstractFormItemRenderer<T> {

    protected AbstractFormFieldRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public void repaint(T renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected void applyErrorStateIfNeeds( IFormField<?> field ) {
		Map<String, Object> params = field.getAttributes();
		if ( !field.getValidationResults().isEmpty() ) {
			for ( IValidationResult result : field.getValidationResults() ) {
				if ( !result.isValid() ) {
					String classValue = (String) params.get("class");
					if ( classValue == null ) {
						params.put("class", "error");
					} else if ( !classValue.contains(" error") ) {
						params.put("class", params.get("class") + " error" );
					}
					break;
				}
			}
		}
	}

}
