package com.redshape.ui.data.stores;

import com.redshape.ui.data.IStore;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.data.stores
 * @date 2/10/12 {3:24 PM}
 */
public class StoresManager extends AbstractStoresManager {

    @Override
    protected <T extends IStore<?>> T createStoreInstance( Class<? extends T> clazz ) {
        if ( clazz == null ) {
            throw new IllegalArgumentException("null");
        }

        T storeInstance;
        try {
            storeInstance = clazz.newInstance();
        } catch ( Throwable e ) {
            storeInstance = null;
        }

        return storeInstance;
    }

}
