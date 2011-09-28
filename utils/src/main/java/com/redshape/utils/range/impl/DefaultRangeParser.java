package com.redshape.utils.range.impl;

import com.redshape.utils.IFunction;
import com.redshape.utils.range.*;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range.impl
 * @date 9/23/11 1:25 PM
 */
public class DefaultRangeParser implements IRangeParser {
	private static final String PARTS_DELIMITER = ",";
	private static final String INTERVAL_DELIMITER = "-";

	@Override
	public <T extends Comparable<T>, V extends IRange<T>> V parse(String value, IFunction<?, T> normalizer ) {
		if ( value == null ) {
			throw new IllegalArgumentException("<null>");
		}

		value = this.normalize(value);

		V result;
		if ( value.contains(PARTS_DELIMITER) ) {
			result = (V) this.processListExpression( value, normalizer );
		} else {
			if ( value.contains(INTERVAL_DELIMITER) ) {
				result = (V) this.processIntervalExpression(value, normalizer);
			} else {
				result = (V) this.processSingularExpression( value, normalizer );
			}
		}

		return result;
	}

	protected String normalize( String value ) {
		return value.replaceAll("\\s", "");
	}

	protected <T extends Comparable<T>> IRange<T> processListExpression( String value, IFunction<?, T> normalizer ) {
		IRangeList<T> list = RangeBuilder.createList();
		String[] parts = value.split(PARTS_DELIMITER);
		for ( String part : parts ) {
			if ( !part.contains(INTERVAL_DELIMITER) ) {
				list.addSubRange( this.processSingularExpression(part, normalizer) );
			} else {
				list.addSubRange( this.processIntervalExpression(part, normalizer) );
			}
		}

		return list;
	}

	protected <T extends Comparable<T>> IRange<T> processSingularExpression(
															String value, IFunction<?, T> normalizer ) {
		try {
			return RangeBuilder.createSingular( normalizer.invoke(value) );
		} catch ( InvocationTargetException e ) {
			throw new IllegalArgumentException("Unable to normalize range part", e.getTargetException() );
		}
	}

	protected <T extends Comparable<T>> IRange<T> processIntervalExpression(
															String value, IFunction<?, T> normalizer ) {
		String[] intervalParts = value.split(INTERVAL_DELIMITER);
		if ( intervalParts.length != 2 ) {
			throw new IllegalArgumentException("Invalid range format");
		}

		/**
		 * @TODO: add some syntax to range expressions
		 * to change selection of IntervalRange.Type value
		 */
		try {
			return RangeBuilder.createInterval(
				IntervalRange.Type.INCLUSIVE,
				normalizer.invoke(intervalParts[0]),
				normalizer.invoke(intervalParts[1])
			);
		} catch ( InvocationTargetException e ) {
			throw new IllegalArgumentException("Unable to normalize interval range part", e.getTargetException() );
		}
	}
}
