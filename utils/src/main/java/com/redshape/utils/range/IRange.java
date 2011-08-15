package com.redshape.utils.range;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:28 PM
 */
public interface IRange<T extends Comparable<?>> {

	public T getStart();

	public T getEnd();

	public boolean inRange( T value );

	public boolean isIntersects( IRange<T> range );

}
