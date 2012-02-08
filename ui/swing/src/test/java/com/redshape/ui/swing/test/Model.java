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
