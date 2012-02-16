package com.redshape.utils.validators.result;

import java.util.Collection;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.result
 */
public interface IResultsList extends IValidationResult, Collection<IValidationResult> {

	public void markValid( boolean value );

}
