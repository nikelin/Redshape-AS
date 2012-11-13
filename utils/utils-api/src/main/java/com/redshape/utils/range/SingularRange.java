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

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String toString() {
		return String.valueOf( this.value );
	}
}
