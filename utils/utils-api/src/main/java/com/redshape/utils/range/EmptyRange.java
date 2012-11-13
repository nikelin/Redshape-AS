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
