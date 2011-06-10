package com.redshape.servlet.form.fields;

public class SelectField<T> extends AbstractSelectField<T> {
	private static final long serialVersionUID = -8484444006979685415L;

	public SelectField() {
		this(null);
	}
	
	public SelectField( String id ) {
		this(id, id);
	}
	
	public SelectField( String id, String name ) {
		super(id, name);
	}
	
}
