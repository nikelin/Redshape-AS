package com.redshape.search.index;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.index
 * @date 9/5/11 2:26 PM
 */
public interface IIndexObjectIdGenerator {

	public <T> T generate( Object entity );

}
