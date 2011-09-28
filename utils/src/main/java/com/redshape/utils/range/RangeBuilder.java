package com.redshape.utils.range;

import com.redshape.utils.IFunction;
import com.redshape.utils.range.impl.DefaultRangeParser;
import com.redshape.utils.range.normalizers.NumericNormalizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:31 PM
 */
public final class RangeBuilder {
	public static IRangeParser parser = new DefaultRangeParser();

	public static <V extends IRange<Integer>> V fromString( String value ) {
		return fromString( value, new NumericNormalizer() );
	}

	public static <T extends Comparable<T>, V extends IRange<T>> V fromString(
			String value, IFunction<?, T> normalizer ) {
		if ( parser == null ) {
			throw new IllegalStateException("Ranges parser not defined!");
		}

		return parser.parse(value, normalizer);
	}

	public static <V extends IRange<Integer>> V fromBitmask( int mask, int size ) {
		return fromBitmask(mask, 0, size);
	}

	public static <V extends IRange<Integer>> V fromBitmask( int mask, int start, int size ) {
		int intervalStart = -1;
		int intervalEnd = -1;
		List<IRange<Integer>> ranges = new ArrayList<IRange<Integer>>();
		for ( int i = start; i <= size; i++ ) {
			if ( (mask & (1 << i) ) != 0 ) {
				if ( intervalStart == -1 ) {
					intervalStart = i;
				} else {
					intervalEnd = i;
				}
			} else {
				if ( intervalStart != -1 ) {
					if ( intervalEnd != -1 ) {
						ranges.add(
							RangeBuilder.<Integer>createInterval(IntervalRange.Type.INCLUSIVE,
																intervalStart, intervalEnd) );
					} else {
						ranges.add(
							RangeBuilder.<Integer>createSingular(intervalStart)
						);
					}

					intervalStart = -1;
					intervalEnd = -1;
				}
			}
		}

		if ( ranges.isEmpty() ) {
			return (V) RangeBuilder.<Integer>createEmptyRange();
		}

		return (V) RangeBuilder.createList( ranges );
	}

	public static <T extends Comparable<T>> IRange<T> createEmptyRange() {
		return new EmptyRange();
	}

	public static <T extends Comparable<T>> IRangeList<T> createList( List<IRange<T>> ranges ) {
		IRangeList<T> list = createList();
		for ( IRange<T> subRange : ranges ) {
			list.addSubRange(subRange);
		}

		return list;
	}

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
