package com.redshape.ui.data;

public class ModelDataTest extends AbstractModelData {
	private static final long serialVersionUID = -398827689028004031L;

	public ModelDataTest() {
		this(null);
	}
	
	public ModelDataTest( String name ) {
		super();
		
		this.setName( name );
	}
	
	public String getName() {
		return this.get( ModelTest.NAME );
	}
	
	public void setName( String name ) {
		this.set( ModelTest.NAME, name );
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
