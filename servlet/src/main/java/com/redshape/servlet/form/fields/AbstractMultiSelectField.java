package com.redshape.servlet.form.fields;

import java.util.ArrayList;
import java.util.List;

public class AbstractMultiSelectField<T> extends AbstractSelectField<T> {
	private static final long serialVersionUID = 3242971188656570012L;

	private List<T> values = new ArrayList<T>();
	
	protected AbstractMultiSelectField() {
		this(null);
	}
	
	protected AbstractMultiSelectField( String id ) {
		this(id, null);
	}
	
	protected AbstractMultiSelectField( String id, String name ) {
		super(id, name);
	}
	
	@Override
	public void setValue( T value ) {
		this.values.add(value);
	}
	
	@Override
	public T getValue() {
		return this.values.get(0);
	}
	
	public void setValues( List<T> values ) {
		this.values.addAll( values );
	}

	public List<T> getValues() {
		return this.values;
	}
}
