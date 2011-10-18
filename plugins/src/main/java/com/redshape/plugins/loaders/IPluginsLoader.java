package com.redshape.plugins.loaders;

import com.redshape.plugins.LoaderException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.loaders
 * @date 10/11/11 12:16 PM
 */
public interface IPluginsLoader {

	public void load() throws LoaderException;

}
