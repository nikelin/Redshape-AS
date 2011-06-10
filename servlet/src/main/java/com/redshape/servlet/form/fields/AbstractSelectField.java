package com.redshape.servlet.form.fields;

import java.util.HashMap;
import java.util.Map;

public class AbstractSelectField<T> extends AbstractField<T> {
	private static final long serialVersionUID = 7948336414439747793L;
	private Map<String, T> options = new HashMap<String, T>();

	public AbstractSelectField() {
		this(null);
	}
	
	public AbstractSelectField( String id ) {
		this(id, id);
	}
	
	public AbstractSelectField( String id, String name ) {
		super(id, name );
	}
	
	public void addOptions( Map<String, T> values ) {
		this.options.putAll( values );
	}
	
	public void addOption( String name, T value ) {
		this.options.put( name, value );
	}
	
	public Map<String, T> getOptions() {
		return this.options;
	}
	
	public void removeOption( String name ) {
		this.options.remove(name);
	}
	
}
