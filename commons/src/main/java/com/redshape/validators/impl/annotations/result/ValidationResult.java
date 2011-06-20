package com.redshape.validators.impl.annotations.result;

import java.lang.annotation.Annotation;

import com.redshape.validators.result.IValidationResult;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators
 */
public class ValidationResult implements IValidationResult {
	private String name;
	private String message;
	private Annotation subject;
	private boolean success;

	public ValidationResult( boolean success, String message ) {
		this.success = success;
		this.message = message;
	}

	public ValidationResult( boolean success ) {
		this(null, null, success);
	}
	
	public ValidationResult( String name ) {
		this( name, null, true );
	}

	public ValidationResult( String name, Annotation subject, boolean success) {
		this.name = name;
		this.subject = subject;
		this.success = success;
	}

	public String getMessage() {
		return this.message;
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
