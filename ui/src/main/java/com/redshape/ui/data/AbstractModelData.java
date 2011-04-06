package com.redshape.ui.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.EventDispatcher;
import com.redshape.ui.events.IEventHandler;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractModelData extends EventDispatcher 
										implements IModelData {
	private static final long serialVersionUID = -843642018276791545L;
	
	private Map<String, Object> values = new HashMap<String, Object>();
    private boolean isDirty;
    private Object relatedObject;
    
    public void setRelatedObject( Object relatedObject ) {
    	this.relatedObject = relatedObject;
    }
    
    @SuppressWarnings("unchecked")
	public <V> V getRelatedObject() {
    	return (V) this.relatedObject;
    }
    
    @Override
    public void set( String name, Object value ) {
    	this.processValue(name, value);
        this.values.put(name, value);
        this.markChanged(name);
    }
    
    protected void processValue( String name, Object value ) {
    	if ( value instanceof IModelData ) {
    		this.processChildModel( name, (IModelData) value);
    	} else if ( value instanceof Collection ) {
    		this.processCollectionValue( name, (Collection<?>) value);
    	} else if ( value.getClass().isArray() 
    			&& IModelData.class.isAssignableFrom( value.getClass().getComponentType() ) ) {
    		this.processArrayValue( name, ( Object[] ) value );
    	}
    }
    
    protected void processArrayValue( String name, final Object[] data ) {
    	this.processCollectionValue( name, Arrays.asList(data) );
    }
    
    protected void processCollectionValue( String name, final Collection<?> data ) {
    	if ( data.isEmpty() ) {
    		return;
    	}
    	
    	Object testObject = null;
    	Iterator<?> iterator = data.iterator();
    	do { 
    		testObject = iterator.next();
    	} while ( testObject == null && iterator.hasNext() );
    	
    	if ( !IModelData.class.isAssignableFrom( testObject.getClass() ) ) {
    		return;
    	}
    	
    	do {
    		this.processChildModel(name, (IModelData) testObject);
    		
    		testObject = iterator.next();
    	} while ( iterator.hasNext() );
    }
    
    protected void processChildModel( final String name, final IModelData model ) {
    	model.addListener( 
			ModelEvent.CHANGED, 
			new IEventHandler() {
				@Override
				public void handle(AppEvent event) {
					AbstractModelData.this.forwardEvent( ModelEvent.CHANGED, name, model );
				}
			}
		);
    }

    protected void markChanged( String fieldName ) {
    	this.makeDirty(true);
    	this.forwardEvent( ModelEvent.CHANGED, fieldName );
    }
    
    @Override
    public void makeDirty( boolean value ) {
    	this.isDirty = value;
    }
    
    @Override
    public boolean isDirty() {
    	return this.isDirty;
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public <V> V get( String name ) {
        return (V) this.values.get(name);
    }

    @SuppressWarnings("unchecked")
	protected <V> Map<String, V> getAll() {
        return (Map<String, V>) this.values;
    }

}
