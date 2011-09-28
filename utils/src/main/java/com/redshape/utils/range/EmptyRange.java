package com.redshape.utils.range;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 9/28/11 3:02 PM
 */
public class EmptyRange<T extends Comparable<T>> implements IRange<T> {
	private static final Number VALUE = 0;

	@Override
	public T getStart() {
		return (T) VALUE;
	}

	@Override
	public T getEnd() {
		return (T) VALUE;
	}

	@Override
	public boolean inRange(T value) {
		return true;
	}

	@Override
	public boolean isIntersects(IRange<T> tiRange) {
		return true;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}

	@Override
	public String toString() {
		return "";
	}
}
