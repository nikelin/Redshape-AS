package com.redshape.ui.data;

public class ModelData extends AbstractModelData {
	private static final long serialVersionUID = -398827689028004031L;

	public ModelData() {
		this(null);
	}
	
	public ModelData(String name) {
		super();
		
		this.setName( name );
	}
	
	public String getName() {
		return this.get( Model.NAME );
	}
	
	public void setName( String name ) {
		this.set( Model.NAME, name );
	}
	
	@Override
	public String toString() {
		return this.getName();
	}
	
}
