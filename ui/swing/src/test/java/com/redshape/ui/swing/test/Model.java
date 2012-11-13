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

package com.redshape.ui.swing.test;

import com.redshape.ui.data.AbstractModelType;
import com.redshape.ui.data.IModelData;

public class Model extends AbstractModelType {
	private static final long serialVersionUID = -8138080885790576458L;
	
	public static final String NAME = "name";
	
	public Model() {
		super( ModelData.class );
		
		this.addField( NAME );
	}
	
	@Override
	public IModelData createRecord() {
		return new ModelData();
	}
	
}
