package com.redshape.ui.data.loaders.policies;

import com.redshape.ui.data.IModelData;
import com.redshape.ui.data.loaders.IDataLoader;

/**
 * @author nikelin
 * @date 26/04/11
 * @package com.redshape.ui.data.loaders.policies
 */
public interface IDataLoaderPolicy<T extends IModelData> {

    public IDataLoader<T> reduce();

}
