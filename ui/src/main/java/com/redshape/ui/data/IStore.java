package com.redshape.ui.data;

import com.redshape.ui.events.IEventDispatcher;
import com.redshape.ui.data.loaders.LoaderException;
import com.redshape.ui.data.stores.StoreEvents;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 09.01.11
 * Time: 18:54
 * To change this template use File | Settings | File Templates.
 */
public interface IStore<V extends IModelData> extends IEventDispatcher {

    public IModelType getType();

    public int count();

    public void add( V record );

    public void remove( V record );

    public V getAt( int index );

    public Collection<V> getList();

    public void load() throws LoaderException;

}
