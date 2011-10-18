package com.redshape.plugins.meta;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.meta
 * @date 10/11/11 1:20 PM
 */
public interface IUpdateInfo {

	public String getDefault();

	public IUpdateSource getSource( String id );

	public List<IUpdateSource> getSources();

}
