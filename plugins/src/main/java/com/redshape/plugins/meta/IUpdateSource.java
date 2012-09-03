package com.redshape.plugins.meta;

import com.redshape.utils.config.IConfig;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 1:21 PM
 */
public interface IUpdateSource {

	public String getId();

	public String getMethod();

	public IConfig getConnection();

}
