package com.redshape.validators.result;

import java.lang.annotation.Annotation;
import java.util.HashSet;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.result
 */
public class ResultsList extends HashSet<IValidationResult>
						 implements IResultsList {
	private String name;
	private boolean success;

	public ResultsList() {
		this(false);
	}

	public ResultsList( boolean success ) {
		this( null, success );
	}

	public ResultsList( String name, boolean success ) {
		super();

		this.name = name;
	}

	@Override
	public Annotation getSubject() {
		throw new UnsupportedOperationException("Operation not supports in current validator implementation");
	}

	@Override
	public void markValid( boolean value ) {
		this.success = value;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isValid() {
		boolean result = true;
		for ( IValidationResult item : this ) {
			result = result && item.isValid();
		}

		return result;
	}
}
