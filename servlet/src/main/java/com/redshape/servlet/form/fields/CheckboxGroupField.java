package com.redshape.servlet.form.fields;

public class CheckboxGroupField<T> extends AbstractMultiSelectField<T> {
	private static final long serialVersionUID = 8858130225808419943L;

	public CheckboxGroupField() {
		this(null);
	}
	
	public CheckboxGroupField( String id ) {
		this(id, id);
	}
	
	public CheckboxGroupField( String id, String name ) {
		super(id, name);
	}

}
