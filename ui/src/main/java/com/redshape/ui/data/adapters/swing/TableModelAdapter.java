package com.redshape.ui.data.adapters.swing;

import com.redshape.ui.events.AppEvent;
import com.redshape.ui.events.IEventHandler;
import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.IStore;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.stores.StoreEvents;

import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.util.Collection;
import java.util.HashSet;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 11.01.11
 * Time: 0:00
 * To change this template use File | Settings | File Templates.
 */
public class TableModelAdapter<T extends IModelData> implements TableModel, IStore<T> {
    private IStore<T> store;
    private Collection<TableModelListener> listeners = new HashSet<TableModelListener>();

    public TableModelAdapter( IStore<T> store ) {
        this.store = store;
    }

    public void add( T record ) {
        this.store.add(record);
    }

    public void remove( T record ) {
        this.store.remove(record);
    }

    public Collection<T> getList() {
        return this.store.getList();
    }

    public String getColumnName( int index ) {
        return this.store.getType().getField(index).getName();
    }

    public Class<?> getColumnClass( int index ) {
        return this.store.getType().getField(index).getType();
    }

    public void setValueAt( Object value, int row, int col ) {
        this.store.getAt(row).set( this.store.getType().getField(col).getName(), value );
    }

    public IModelType getType() {
        return this.store.getType();
    }

    public int getColumnCount() {
        return this.store.getType().count();
    }

    public boolean isCellEditable( int row, int col ) {
        return false;
    }

    public Object getValueAt( int row, int col ) {
        return this.store.getAt(row).<T>get(this.store.getType().getField(col).getName());
    }

    public T getAt( int index ) {
        return this.store.getAt(index);
    }

    public Object getValueAt( int index ) {
        return this.store.getAt(index);
    }

    public void load() throws LoaderException {
        this.store.load();
    }

    public void addListener( StoreEvents type, IEventHandler<StoreEvents> handler ) {
        this.store.addListener( type, handler );
    }

    public int count() {
        return this.store.count();
    }

    public int getRowCount() {
        return this.store.count();
    }

    public void removeTableModelListener( TableModelListener listener ) {
        this.listeners.remove(listener);
    }

    public void addTableModelListener( TableModelListener listener ) {
        this.listeners.add( listener );
    }

    public void forwardEvent( StoreEvents type, Object... args ) {
        this.store.forwardEvent( type, args );
    }

    public void forwardEvent( AppEvent<StoreEvents> event ) {
        this.store.forwardEvent( event );
    }

}
