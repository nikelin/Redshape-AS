package com.redshape.servlet.form.fields;

public class CheckboxField extends AbstractSelectField<Boolean> {
	private static final long serialVersionUID = -396074450586493270L;

	public CheckboxField() {
		this(null);
	}
	
	public CheckboxField( String id ) {
		this(id, id);
	}
	
	public CheckboxField( String id, String value ) {
		super(id, value);
	}

}
