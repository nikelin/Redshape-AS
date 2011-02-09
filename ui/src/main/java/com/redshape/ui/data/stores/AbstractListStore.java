package com.redshape.ui.data.stores;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.IModelType;
import com.redshape.ui.data.loaders.ILoaderConfig;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 11.01.11
 * Time: 0:40
 * To change this template use File | Settings | File Templates.
 */
public class AbstractListStore<T extends IModelData> extends ListStore<T> {

    public AbstractListStore(IModelType type, ILoaderConfig config) {
        super( type, null );
    }

}
