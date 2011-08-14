package com.redshape.utils.range;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:31 PM
 */
public final class RangeBuilder {

	public static <T extends Comparable<T>> IRange<T> createSingular(T value) {
		return new SingularRange( value );
	}

	public static <T extends Comparable<T>> IRange<T> createInterval( IntervalRange.Type type, T start, T end) {
		return new IntervalRange(type, start, end);
	}

	public static <T extends Comparable<T>> IRangeList<T> createList() {
		return new ListRange();
	}
}
