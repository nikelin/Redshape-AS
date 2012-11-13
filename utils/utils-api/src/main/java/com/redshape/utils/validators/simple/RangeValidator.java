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

package com.redshape.utils.validators.simple;

import com.redshape.utils.ILambda;
import com.redshape.utils.range.IRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.utils.range.RangeUtils;
import com.redshape.utils.range.normalizers.NumericNormalizer;
import com.redshape.utils.validators.AbstractValidator;
import com.redshape.utils.validators.result.ValidationResult;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.validators.impl.common
 * @date 8/16/11 8:49 PM
 */
public class RangeValidator extends AbstractValidator<String, ValidationResult> {
	private IRange<Integer> range;
	private ILambda<Integer> normalizer;

	public RangeValidator( IRange<Integer> range ) {
		this(range, new NumericNormalizer() );
	}

	public RangeValidator( IRange<Integer> range, ILambda<Integer> normalizer ) {
		if ( range == null ) {
			throw new IllegalArgumentException("<null>");
		}

		this.normalizer = normalizer;
		this.range = range;
	}

	@Override
	public boolean isValid(String value) {
		return value == null || value.isEmpty()
				|| RangeUtils.checkIntersections( range, RangeBuilder.fromString(value, this.normalizer) );
	}

	@Override
	public ValidationResult validate(String value) {
		return new ValidationResult( this.isValid(value),
				"Given value not fit acceptable values range: " + this.range.getStart() + " - " + this.range.getEnd() );
	}

}
