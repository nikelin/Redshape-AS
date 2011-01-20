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
    private Map<String, IModelField> values = new HashMap<String, IModelField>();
    private List<String> order = new ArrayList<String>();

    public IModelField getField( int index ) {
        return this.values.get( this.order.get(index) );
    }

    public IModelField getField( String name ) {
        return this.values.get(name);
    }

    public void removeField( String name ) {
        this.values.remove(name);
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
        private String name;
        private String format;
        private Class<?> type;
        private boolean required;

        public ModelTypeField( String name ) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setFormat( String format ) {
            this.format = format;
        }

        public String getFormat() {
            return this.format;
        }

        public void setRequired( boolean required ) {
            this.required = required;
        }

        public boolean isRequired() {
            return this.required;
        }

        public void setType( Class<?> type ) {
            this.type = type;
        }

        public Class<?> getType() {
            return this.type;
        }
    }

}
