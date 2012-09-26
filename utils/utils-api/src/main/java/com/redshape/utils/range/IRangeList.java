package com.redshape.utils.range;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:59 PM
 */
public interface IRangeList<T extends Comparable<T>> extends IRange<T> {

	public void addSubRange( IRange<T> range );

	public List<IRange<T>> getSubRanges();

}
