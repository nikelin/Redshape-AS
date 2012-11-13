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

package com.redshape.ui.data;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:22
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractModelType implements IModelType {
	private static final long serialVersionUID = 2639761182581273129L;
	
	private Map<String, IModelField> values = new HashMap<String, IModelField>();
    private List<String> order = new ArrayList<String>();
    
    private Class<? extends IModelData> type;
    
    public AbstractModelType( Class<? extends IModelData> type ) {
    	this.type = type;
    }
    
    @Override
	public boolean isInstance(Class<? extends IModelData> type) {
        /**
         * @FIXME
         */
		return true;
	}

	@Override
	public boolean isInstance(IModelData type) {
		return this.isInstance( type.getClass() );
	}

	public IModelField getField( int index ) {
        return this.values.get( this.order.get(index) );
    }

    public IModelField getField( String name ) {
        return this.values.get(name);
    }

    public void removeField( String name ) {
        this.values.remove(name);
    }

    public int nonTransientCount() {
    	int size = 0;
    	for ( IModelField field : this.getFields() ) {
    		if ( !field.isTransient() ) {
    			size++;
    		}
    	}
    	
    	return size;
    }
    
    public int count() {
        return this.values.size();
    }

    public IModelField addField( String name ) {
        if ( this.values.containsKey(name) ) {
            return null;
        }

        IModelField field;
        this.values.put(name, field = this.createField( name ) );
        this.order.add(name);

        return field;
    }

    protected IModelField createField( String name ) {
        return new ModelTypeField( name );
    }

    public void remove( String name ) {
        this.values.remove(name);
    }

    public Collection<IModelField> getFields() {
        return this.values.values();
    }

    public class ModelTypeField implements IModelField {
		private static final long serialVersionUID = 1867419046675566804L;
		
		private String name;
        private String format;
        private String title;
        private Class<?> type;
        private boolean required;
        private boolean isTransient;
        private boolean isList;

        public ModelTypeField( String name ) {
            this.name = name;
        }
        
        public IModelField markList( boolean value ) {
        	this.isList = value;
        	return this;
        }
        
        public boolean isList() {
        	return this.isList;
        }
        
        public String getTitle() {
        	return this.title;
        }
        
        public IModelField setTitle( String title ) {
        	this.title = title;
        	return this;
        }
        
        public boolean isTransient() {
        	return this.isTransient;
        }
        
        public IModelField makeTransient( boolean status ) {
        	this.isTransient = status;
        	return this;
        }

        public String getName() {
            return this.name;
        }

        public IModelField setFormat( String format ) {
            this.format = format;
            return this;
        }

        public String getFormat() {
            return this.format;
        }

        public IModelField setRequired( boolean required ) {
            this.required = required;
            return this;
        }

        public boolean isRequired() {
            return this.required;
        }

        public IModelField setType( Class<?> type ) {
            this.type = type;
            return this;
        }

        public Class<?> getType() {
            return this.type;
        }
    }

}
