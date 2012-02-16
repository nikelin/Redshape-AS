package com.redshape.utils.range;

import com.redshape.utils.IFunction;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 9/23/11 1:25 PM
 */
public interface IRangeParser {

	public <T extends Comparable<T>, V extends IRange<T>> V parse( String value, IFunction<?, T> normalizer  );

}
