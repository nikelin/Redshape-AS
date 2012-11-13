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

package com.redshape.utils.beans.bindings.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collection;

import com.redshape.utils.beans.bindings.BindingException;
import com.redshape.utils.beans.bindings.accessors.AccessException;
import com.redshape.utils.beans.bindings.annotations.BindableAttributes;

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
