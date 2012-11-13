/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils.range.impl;

import com.redshape.utils.ILambda;
import com.redshape.utils.InvocationException;
import com.redshape.utils.range.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range.impl
 * @date 9/23/11 1:25 PM
 */
public class DefaultRangeParser implements IRangeParser {
	private static final String PARTS_DELIMITER = ",";
	private static final String INTERVAL_DELIMITER = "-";

	@Override
	public <T extends Comparable<T>, V extends IRange<T>> V parse(String value, ILambda<T> normalizer ) {
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
		return value.trim()
                  .replaceAll("\\s-\\s", "-")
                  .replaceAll("\\s,\\s", ",")
                  .replaceAll("\\s", ",");
	}

	protected <T extends Comparable<T>> IRange<T> processListExpression( String value, ILambda<T> normalizer ) {
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
															String value, ILambda<T> normalizer ) {
		try {
			return RangeBuilder.createSingular( normalizer.invoke(value) );
		} catch ( InvocationException e ) {
			throw new IllegalArgumentException("Unable to normalize range part", e );
		}
	}

	protected <T extends Comparable<T>> IRange<T> processIntervalExpression(
															String value, ILambda<T> normalizer ) {
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
		} catch ( InvocationException e ) {
			throw new IllegalArgumentException("Unable to normalize interval range part", e );
		}
	}
}
