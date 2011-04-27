package com.redshape.bindings.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;

import com.redshape.bindings.BindingException;
import com.redshape.bindings.accessors.AccessException;
import com.redshape.bindings.annotations.BindableAttributes;

public interface IBindable extends Serializable {

    public boolean hasAttribute( BindableAttributes attribute );

    public Collection<BindableAttributes> getAttributes();

	public boolean isComposite();
	
	public boolean isReadable();
	
	public boolean isWritable();
	
	public BindableType getMetaType();
	
	public Class<?> getType();
	
	public String getId();
	
	public String getName();
	
	public <T> T read( Object context ) throws AccessException;
	
	public void write( Object context, Object value ) throws AccessException;
	
	public boolean isMap();
	
	public boolean isCollection();
	
	public IBindableMap asMapObject() throws BindingException;
	
	public IBindableCollection asCollectionObject() throws BindingException;

	public Collection<Annotation> getAnnotations();
	
}
