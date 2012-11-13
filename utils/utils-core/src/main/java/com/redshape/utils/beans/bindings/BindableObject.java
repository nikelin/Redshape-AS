/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils.beans.bindings;

import com.redshape.utils.beans.bindings.accessors.AccessException;
import com.redshape.utils.beans.bindings.accessors.IPropertyReader;
import com.redshape.utils.beans.bindings.accessors.IPropertyWriter;
import com.redshape.utils.beans.bindings.annotations.BindableAttributes;
import com.redshape.utils.beans.bindings.types.BindableType;
import com.redshape.utils.beans.bindings.types.CollectionType;
import com.redshape.utils.beans.bindings.types.IBindable;
import com.redshape.utils.beans.bindings.types.IBindableCollection;
import com.redshape.utils.beans.bindings.types.IBindableMap;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;

class BindableObject implements IBindable, IBindableMap, IBindableCollection {
	private static final long serialVersionUID = 848980700172296050L;
	
	private String id;
	private String name;
	private Class<?> type;
	private BindableType metaType;

    private Collection<BindableAttributes> attributes = new HashSet<BindableAttributes>();

	private IPropertyReader reader;
	private IPropertyWriter writer;

	// Collection specific
	private CollectionType collectionType;
	private Class<?> elementType;
	private Collection<Annotation> annotations = new HashSet<Annotation>();
	
	// Map specific
	private String keyName;
	private Class<?> keyType;
	private String valueName;
	private Class<?> valueType;

	public BindableObject(IPropertyReader reader, IPropertyWriter writer) {
		this.reader = reader;
		this.writer = writer;
	}

    public void setAttributes( Collection<BindableAttributes> attributes ) {
        this.attributes = attributes;
    }

    @Override
    public Collection<BindableAttributes> getAttributes() {
        return this.attributes;
    }

    @Override
    public boolean hasAttribute( BindableAttributes attribute ) {
        return this.attributes.contains(attribute);
    }

    public void addAttribute( BindableAttributes attribute ) {
        this.attributes.add(attribute);
    }

	public void setAnnotations( Collection<Annotation> annotations ) {
		this.annotations = annotations;
	}

	@Override
	public Collection<Annotation> getAnnotations() {
		return this.annotations;
	}

	protected IPropertyReader getReader() {
		return this.reader;
	}

	protected IPropertyWriter getWriter() {
		return this.writer;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	@Override
	public void setCollectionType( CollectionType type ) throws BindingException {
		if ( !this.isCollection() ) {
			throw new BindingException(
				"Only collections allowed to have element type property!");
		}
		
		this.collectionType = type;
	}
	
	@Override
	public CollectionType getCollectionType() throws BindingException  {
		if ( !this.isCollection() ) {
			throw new BindingException(
				"Only collections allowed to have element type property!");
		}
		
		return this.collectionType;
	}
	
	@Override
	public Class<?> getElementType() throws BindingException {
		if (!this.isCollection()) {
			throw new BindingException(
					"Only collections allowed to have element type property!");
		}

		return this.elementType;
	}

	public void setElementType(Class<?> type) throws BindingException {
		if (!this.isCollection()) {
			throw new BindingException(
					"Only collections allowed to have element type property!");
		}

		this.elementType = type;
	}

	@Override
	public String getKeyName() {
		return this.keyName;
	}

	public void setKeyName(String name) {
		this.keyName = name;
	}

	@Override
	public Class<?> getKeyType() {
		return this.keyType;
	}

	public void setKeyType(Class<?> keyType) {
		this.keyType = keyType;
	}

	@Override
	public String getValueName() {
		return this.valueName;
	}

	public void setValueName(String name) {
		this.valueName = name;
	}

	@Override
	public Class<?> getValueType() {
		return this.valueType;
	}

	public void setValueType(Class<?> valueType) throws BindingException {
		if (!this.isMap()) {
			throw new BindingException(
					"Only maps allowed to have value type property!");
		}

		this.valueType = valueType;
	}

	@Override
	public BindableType getMetaType() {
		return this.metaType;
	}

	public void setMetaType(BindableType type) {
		this.metaType = type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	@Override
	public Class<?> getType() {
		return this.type;
	}

	@Override
	public <T> T read(Object context) throws AccessException {
		if ( !this.isWritable() ) {
			throw new AccessException("Object is write-only");
		}
		
		return this.getReader().<T>read(context);
	}

	@Override
	public void write(Object context, Object value) throws AccessException {
		if ( value == null ) {
			return;
		}

		if ( !this.isWritable() ) {
			throw new AccessException("Object " + 
							( this.getId() == null 
										? "<null>" : this.getId() ) + " is read-only");
		}
		
		if (!this.getWriter().isConsistent(value.getClass())) {
			throw new AccessException("Inconsistent value given");
		}

		this.getWriter().write(context, value);
	}

	@Override
	public boolean isComposite() {
		return this.metaType.equals(BindableType.COMPOSITE);
	}
	
	@Override
	public boolean isMap() {
		return this.metaType.equals(BindableType.MAP);
	}

	@Override
	public boolean isCollection() {
		return this.metaType.equals(BindableType.LIST);
	}

	@Override
	public IBindableMap asMapObject() throws BindingException {
		if (!this.isMap()) {
			throw new BindingException(
					"Current object cannot be converted to Map!");
		}

		return this;
	}

	@Override
	public IBindableCollection asCollectionObject() throws BindingException {
		if (!this.isCollection()) {
			throw new BindingException(
					"Current object cannot be converted to Collection!");
		}

		return this;
	}

	@Override
	public boolean isReadable() {
		return this.getReader() != null;
	}

	@Override
	public boolean isWritable() {
		return this.getWriter() != null;
	}
	
	@Override
	public String toString() {
		return this.getId();
	}

}
