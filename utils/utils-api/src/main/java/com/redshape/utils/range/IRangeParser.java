package com.redshape.utils.range;

import com.redshape.utils.ILambda;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 9/23/11 1:25 PM
 */
public interface IRangeParser {

	public <T extends Comparable<T>, V extends IRange<T>> V parse( String value, ILambda<T> normalizer  );

}
