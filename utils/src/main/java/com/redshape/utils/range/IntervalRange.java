package com.redshape.utils.range;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:42 PM
 */
public class IntervalRange<T extends Comparable<T>> implements IRange<T> {
	public enum Type {
		INCLUSIVE,
		EXCLUSIVE
	}

	private T start;
	private T end;
	private Type type;

	public IntervalRange( IntervalRange.Type type, T start, T end ) {
		this.start = start;
		this.end = end;
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	public T getStart() {
		return start;
	}

	public T getEnd() {
		return end;
	}

	@Override
	public boolean inRange(T value) {
		switch ( this.getType() ) {
			case INCLUSIVE:
				return value.compareTo( this.start ) != -1
						&& value.compareTo( this.end ) != 1;
			case EXCLUSIVE:
				return value.compareTo( this.start ) == 1
						&& value.compareTo( this.end ) == -1;
			default:
				throw new IllegalArgumentException("Unsupported interval range type");
		}
	}

	@Override
	public boolean isIntersects(IRange<T> tiRange) {
		return RangeUtils.checkIntersections(this, tiRange);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append( this.start )
			   .append( "-" )
			   .append( this.end );

		return builder.toString();
	}
}
