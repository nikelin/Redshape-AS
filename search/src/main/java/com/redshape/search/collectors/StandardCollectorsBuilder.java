package com.redshape.search.collectors;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.collectors
 * @date 9/5/11 3:33 PM
 */
public class StandardCollectorsBuilder implements ICollectorsBuilder {

	/**
	 * @TODO Implement
	 * @param <T>
	 * @return
	 */
	@Override
	public <T extends IResultsCollector> T createCollector() {
		return (T) new StandardCollector();
	}
}
