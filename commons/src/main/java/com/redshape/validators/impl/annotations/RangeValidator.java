package com.redshape.validators.impl.annotations;

import com.redshape.utils.AnnotatedObject;
import com.redshape.validators.AbstractValidator;
import com.redshape.validators.annotations.Range;
import com.redshape.validators.impl.annotations.result.ValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.impl
 */
public class RangeValidator extends AbstractValidator<AnnotatedObject, ValidationResult> {

	@Override
	public boolean isValid(AnnotatedObject value) {
		Range annotation = value.getAnnotation( Range.class );

		if ( !( value.getContext() instanceof Comparable) ) {
			return true;
		}

		int maxCompare = value.<Comparable<Integer>>getContext().compareTo( annotation.max() );
		if ( maxCompare > 0 ) {
			return false;
		}

		int minCompare = value.<Comparable<Integer>>getContext().compareTo( annotation.min() );
		if ( minCompare < 0 ) {
			return false;
		}

		return true;
	}

	@Override
	public ValidationResult validate(AnnotatedObject value) {
		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
