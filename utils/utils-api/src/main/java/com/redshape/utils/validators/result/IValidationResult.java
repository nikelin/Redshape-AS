package com.redshape.utils.validators.result;

import java.io.Serializable;

/**
 * Represents simple validation result.
 * 
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.result
 */
public interface IValidationResult extends Serializable {
	
	public boolean isValid();

	public String getMessage();
	
}
