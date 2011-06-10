package com.redshape.servlet.views;

public class ViewAttributes {
	private String name;
	
	protected ViewAttributes( String name ) {
		this.name = name;
	}
	
	public static final ViewAttributes PAGE_TITLE = new ViewAttributes("Attribute.Page.Title");
	
	public String name() {
		return this.name;
	}
	
	@Override
	public String toString() {
		return this.name();
	}
	
}
