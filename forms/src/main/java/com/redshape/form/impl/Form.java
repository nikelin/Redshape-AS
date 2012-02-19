package com.redshape.form.impl;

import com.redshape.form.*;
import com.redshape.form.decorators.IDecorator;
import com.redshape.utils.Commons;
import com.redshape.utils.SimpleStringUtils;

import java.util.*;

public class Form extends AbstractFormItem implements IForm {
    private static final long serialVersionUID = 5015229816321663639L;

    public enum FormState {
        NONE,
        RENDERED,
        VALIDATED,
        PROCESSED
    }

    public Form(String id) {
        this(id, id);
    }

    public Form( String id, String name ) {
        super(id, name );
    }

    private FormState state;
    private String action;
    private String method;
    private String legend;
    private List<IFormItem> items = new ArrayList<IFormItem>();
    private Map<String, Integer> itemsDict = new HashMap<String, Integer>();
    private IFormProcessHandler handler;

    @Override
    public void setProcessHandler( IFormProcessHandler handler ) {
        this.handler = handler;
    }

    protected IFormProcessHandler getProcessHandler() {
        return this.handler;
    }

    @Override
    public void process( IUserRequest request ) throws InvalidDataException {
        if ( !request.getMethod().equals( this.getMethod()) ) {
            throw new IllegalArgumentException("Processing only possible in "
                    + this.getMethod() + " context");
        }

        this.resetState();

        Map<String, Object> parameters = request.getParameters();
        for ( String attribute : parameters.keySet() ) {
            this.setValue(attribute, parameters.get(attribute) );
        }

        if ( !this.isValid() ) {
            throw new InvalidDataException();
        }

        if ( this.getProcessHandler() != null ) {
            this.getProcessHandler().process( this );
        }
    }

    @Override
    public <T> List<T> getValues( String path ) {
        if ( path == null ) {
            throw new IllegalArgumentException("<null>");
        }

        IFormField<T> field = this.<T, IFormField<T>>findField( path );
        if ( field == null ) {
            throw new IllegalArgumentException("Path " + path + " not exists!");
        }

        if ( !field.hasMultiValues() ) {
            throw new IllegalStateException("Field " + path + " can handle only single value!");
        }

        return field.getValues();
    }

    @Override
    public <T> T getValue( String path ) {
        if ( path == null ) {
            throw new IllegalArgumentException("<null>");
        }

        IFormField<T> field = this.<T, IFormField<T>>findField(path);
        if ( field == null ) {
            throw new IllegalArgumentException("Path " + path + " not exists!");
        }

        return field.getValue();
    }

    @Override
    public boolean hasValue( String path ) {
        return this.getValue(path) != null
                && !this.getValue(path).equals("null")
                && !String.valueOf(this.getValue(path)).isEmpty();
    }

    @Override
    public <T> void setValue( String path, T value ) {
        path = path.replace("[]", "");
        if ( path == null ) {
            throw new IllegalArgumentException("<null>");
        }

        IFormField<T> field = this.<T, IFormField<T>>findField(path);
        if ( field == null ) {
            throw new IllegalArgumentException("Path " + path + " not exists!");
        }

        field.setValue(value);
    }

    @Override
    public <T, V extends IFormField<T>> V findField( String path ) {
        String[] parts = path.split("\\.");
        if ( parts.length != 1 ) {
            return this.<T, V>findField( this, parts );
        }

        return this.<T, V>findField( this, path );
    }

    @Override
    public <T extends IForm> T findContext( String path ) {
        return this.<T>findContext( path.split("\\.") );
    }

    protected <T extends IForm> T findContext( String[] path ) {
        Integer index = this.itemsDict.get( path[0] );

        IFormItem item = null;
        if ( index != null ) {
            item = this.items.get( index );
        } else {
            if ( this.getCanonicalName().equals( path[0] ) ) {
                item = this;
            }
        }

        if ( item == null ) {
            throw new IllegalStateException();
        }

        if (  !( item instanceof IForm ) ) {
            throw new IllegalArgumentException("Illegal path");
        }

        if ( path.length == 1 ) {
            return (T) item;
        }

        return ( (IForm) item ).<T>findContext(
                SimpleStringUtils.join(Arrays.asList(path).subList(1, path.length), ".") );
    }

    protected <T, V extends IFormField<T>> V findField( IForm context, String[] name ) {
        IForm targetContext = this.findContext( (String[]) Arrays.asList(name)
                                                .subList(0, name.length - 1).toArray() );
        if ( targetContext == null ) {
            if ( name.length == 2
                    && name[0].equals( Commons.select( this.getName(), this.getId() ) ) ) {
                targetContext = this;
            } else {
                throw new IllegalArgumentException("Path " + Arrays.asList(name) + " not founded!");
            }
        }

        return this.<T, V>findField( targetContext, name[name.length-1] );
    }

    @SuppressWarnings("unchecked")
    protected <T, V extends IFormField<T>> V findField( IForm context, String subContextName ) {
        V item = null;

        if ( context != this ) {
            item = context.<T, V>findField( subContextName );
        } else {
            Integer index = Commons.select( this.itemsDict.get(subContextName),
                    this.itemsDict.get( subContextName + "[]" ) );
            if ( index != null ) {
                item = (V) this.items.get(index);
            }
        }

        if ( item == null ) {
            return null;
        }

        if ( !( item instanceof IFormField ) ) {
            throw new IllegalArgumentException("Requested path not related to field");
        }

        return (V) item;
    }

    protected String getItemId( IFormItem field ) {
        String id = Commons.select( field.getName(), field.getId() );
        if ( id == null ) {
            return null;
        }

        return id.replace("[]", "");
    }

    @Override
    public void resetMessages() {
        for ( IFormItem item : this.getItems() ) {
            item.resetMessages();
        }
    }

    @Override
    public void resetState() {
        this.state = FormState.NONE;
        this.clearMessages();

        for ( IFormItem item : this.getItems() ) {
            item.resetState();
        }
    }

    @Override
    public void setLegend(String legend) {
        this.legend = legend;
    }

    @Override
    public String getLegend() {
        return this.legend;
    }

    @Override
    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getAction() {
        return this.action;
    }

    @Override
    public void setMethod(String method) {
        this.method = method;
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void addField(IFormField<?> field) {
        field.setContext(this);
        this.items.add( field );
        this.itemsDict.put( this.getItemId(field), this.items.size() - 1 );
    }

    @Override
    public void removeField(IFormField<?> field) {
        this.itemsDict.remove( this.getItemId(field) );
        this.items.remove( field );
        this.updateItemsDict();
    }

    @Override
    public List<IFormField<?>> getFields() {
        List<IFormField<?>> fields = new ArrayList<IFormField<?>>();
        for ( IFormItem item : this.items ) {
            if ( item instanceof IFormField ) {
                fields.add( (IFormField) item );
            }
        }

        return fields;
    }

    @Override
    public void addSubForm(IForm form, String name) {
        form.setContext(this);
        form.setName( name );

        this.items.add( form );
        this.itemsDict.put( name, this.items.size() - 1 );
        this.updateItemsDict();
    }

    @Override
    public void removeSubForm(String name) {
        IFormItem result = null;
        for ( IFormItem item : this.getItems() ) {
            if ( item.getName().equals( name ) ) {
                if ( !( item instanceof IForm ) ) {
                    throw new IllegalArgumentException("Given name not related to a subform");
                }

                result = item;
                break;
            }
        }

        if ( result == null ) {
            throw new IllegalArgumentException("Item not found");
        }

        this.itemsDict.remove( this.getItemId(result) );
        this.items.remove( result );
        this.updateItemsDict();
    }

    @Override
    public List<IForm> getSubForms() {
        List<IForm> result = new ArrayList<IForm>();
        for ( IFormItem item : this.items ) {
            if ( item instanceof IForm ) {
                result.add( (IForm) item );
            }
        }

        return result;
    }

    @Override
    public List<IFormItem> getItems() {
        return this.items;
    }

    @Override
    public boolean isValid() {
        boolean result = true;
        for ( IFormItem item : this.getItems() ) {
            boolean itemResult = item.isValid();
            result = itemResult && result;
        }

        return result;
    }

    @Override
    public void removeField(String path) {
        IFormField<?> field = this.<Object, IFormField<Object>>findField(path);
        if ( field == null ) {
            throw new IllegalArgumentException("Field not found");
        }

        this.removeField(field);
    }

    @Override
    public void removeContext(String path) {
        this.findContext(path).remove();
    }

    @Override
    public void remove() {
        if ( this.getContext() == null ) {
            throw new IllegalArgumentException("Unable to remove root form context");
        }

        this.getContext().removeSubForm( this.getName() );
    }

    @Override
    public void copy( IForm form ) {
        this.setAction( form.getAction() );
        this.setMethod( form.getMethod() );

        for ( IFormItem item : form.getItems() ) {
            if ( item instanceof IFormField ) {
                this.addField( (IFormField<?>) item );
            } else if ( item instanceof IForm ) {
                this.addSubForm( (IForm) item, item.getName() );
            }
        }

        for ( IDecorator decorator : this.getDecorators() ) {
            this.setDecorator( decorator );
        }
    }

    // Hmm, maybe it's must be changed in a some way...
    protected void updateItemsDict() {
        int i = 0;
        for ( IFormItem item : this.items ) {
            this.itemsDict.put( this.getItemId(item), i++ );
        }
    }

}
