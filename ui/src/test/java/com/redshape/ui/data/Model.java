package com.redshape.ui.data;

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
