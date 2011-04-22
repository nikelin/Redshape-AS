package com.redshape.validators.result;

import java.lang.annotation.Annotation;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class ValidationResult implements IValidationResult {
	private String name;
	private Annotation subject;
	private boolean success;

	public ValidationResult( boolean success ) {
		this( null, null, success );
	}

	public ValidationResult( String name ) {
		this( name, null, true );
	}

	public ValidationResult( String name, Annotation subject, boolean success) {
		this.name = name;
		this.subject = subject;
		this.success = success;
	}

	public Annotation getSubject() {
		return this.subject;
	}

	public String getName() {
		return this.name;
	}

	public boolean isValid() {
		return this.success;
	}

}
