package com.redshape.validators.result;

import java.lang.annotation.Annotation;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.result
 */
public interface IValidationResult {

	public Annotation getSubject();

	public String getName();

	public boolean isValid();

}
