package com.redshape.ui.gwt.data.stores;

import com.redshape.ui.data.IStore;
import com.redshape.ui.data.stores.AbstractStoresManager;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt.data.stores
 * @date 2/10/12 {3:31 PM}
 */
public class GwtStoresManager extends AbstractStoresManager {

    @Override
    protected <T extends IStore<?>> T createStoreInstance(Class<? extends T> clazz) {
        throw new UnsupportedOperationException();
    }

}
