package com.redshape.search.collectors;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.collectors
 * @date 9/5/11 3:32 PM
 */
public interface ICollectorsBuilder {

	public <T extends IResultsCollector> T createCollector();

}
