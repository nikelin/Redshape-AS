package com.redshape.ui.data.loaders;

import com.redshape.ui.events.IEventDispatcher;
import com.redshape.ui.data.IModelData;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 13:12
 * To change this template use File | Settings | File Templates.
 */
public interface IDataLoader<T extends IModelData> extends IEventDispatcher, Serializable {

    public void load() throws LoaderException;

}