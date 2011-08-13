package com.redshape.servlet.form.fields;

import org.apache.commons.collections.OrderedMap;
import org.apache.commons.collections.map.LinkedMap;

import java.util.Map;

public class AbstractSelectField<T> extends AbstractField<T> {
	private static final long serialVersionUID = 7948336414439747793L;
	private OrderedMap options = new LinkedMap();

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
