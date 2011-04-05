package com.redshape.bindings;

import com.redshape.bindings.accessors.AccessException;
import com.redshape.bindings.accessors.IPropertyReader;
import com.redshape.bindings.accessors.IPropertyWriter;
import com.redshape.bindings.types.BindableType;
import com.redshape.bindings.types.CollectionType;
import com.redshape.bindings.types.IBindable;
import com.redshape.bindings.types.IBindableCollection;
import com.redshape.bindings.types.IBindableMap;

class BindableObject implements IBindable, IBindableMap, IBindableCollection {
	private String id;
	private String name;
	private Class<?> type;
	private BindableType metaType;

	private IPropertyReader reader;
	private IPropertyWriter writer;

	// Collection specific
	private CollectionType collectionType;
	private Class<?> elementType;
	
	// Map specific
	private String keyName;
	private Class<?> keyType;
	private String valueName;
	private Class<?> valueType;

	public BindableObject(IPropertyReader reader, IPropertyWriter writer) {
		this.reader = reader;
		this.writer = writer;
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
		
		return this.getReader().read(context);
	}

	@Override
	public void write(Object context, Object value) throws AccessException {
		if ( !this.isWritable() ) {
			throw new AccessException("Object is read-only");
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
