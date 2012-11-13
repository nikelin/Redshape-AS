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

package com.redshape.utils.range;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:43 PM
 */
public class ListRange<T extends Comparable<T>> implements IRangeList<T> {
	private List<IRange<T>> ranges = new ArrayList<IRange<T>>();

	@Override
	public void addSubRange(IRange<T> subRange) {
		this.ranges.add( subRange );
	}

	@Override
	public List<IRange<T>> getSubRanges() {
		return this.ranges;
	}

	@Override
	public boolean inRange(T value) {
		for ( IRange<T> subRange : this.getSubRanges() ) {
			if ( subRange.inRange(value) ) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEmpty() {
		return this.getSubRanges().isEmpty();
	}

	@Override
	public T getStart() {
		throw new UnsupportedOperationException("Operation not supported on ListRange type");
	}

	@Override
	public T getEnd() {
		throw new UnsupportedOperationException("Operation not supported on ListRange type");
	}

	@Override
	public boolean isIntersects(IRange<T> tiRange) {
		return RangeUtils.checkIntersections( this, tiRange );
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		int i = 0;
		for ( IRange<T> range : this.getSubRanges() ) {
			builder.append( range.toString() );

			if ( i++ != (this.getSubRanges().size() - 1) ) {
		   		builder.append(",");
			}
		}

		return builder.toString();
	}
}
