package com.redshape.ui.data;

public class ModelTest extends AbstractModelType {

	public static final String NAME = "name";
	
	public ModelTest() {
		super();
		
		this.addField( NAME );
	}
	
	@Override
	public IModelData createRecord() {
		return new ModelDataTest();
	}
	
}
