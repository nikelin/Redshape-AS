package com.redshape.utils.range;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:34 PM
 */
public class SingularRange<T extends Comparable<T>> implements IRange<T> {
	private T value;

	public SingularRange( T value ) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	@Override
	public T getStart() {
		return this.getValue();
	}

	@Override
	public T getEnd() {
		return this.getValue();
	}

	@Override
	public boolean inRange(T value) {
		return this.value.equals(value);
	}

	@Override
	public boolean isIntersects(IRange<T> range) {
		return RangeUtils.checkIntersections( this, range );
	}
}
