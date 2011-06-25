package com.redshape.servlet.form;

import com.redshape.servlet.form.render.IFormFieldRenderer;
import com.redshape.validators.IValidator;
import com.redshape.validators.result.IValidationResult;

import java.util.Collection;

/**
 * Interface to represent form field such as textfield, select, etc.
 * 
 * @author nikelin
 *
 * @param <T>
 */
public interface IFormField<T> extends IFormItem {

    /**
     * Value of field validity
     * @return
     */
    public boolean isRequired();

    /**
     * Mark field as required as request member and fully-valid
     * @param value
     */
    public void setRequired( boolean value );

	/**
	 * Add new validator to constraint current field value ranges.
	 * 
	 * @param validator
	 */
	public void addValidator( IValidator<T, IValidationResult> validator );
	
	/**
	 * Add validators collection to constraint current field value range.
	 * 
	 * @param validators
	 */
	public void addValidators( Collection<IValidator<T, IValidationResult>> validators );
	
	/**
	 * Remove all related validators
	 */
	public void clearValidators();
	
	/**
	 * Remove specified validator
	 * @param validator
	 */
	public void removeValidator( IValidator<T, IValidationResult> validator );
	
	/**
	 * Return results of validation for all validators
	 * @return
	 */
	public Collection<IValidationResult> getValidationResults();
	
	/**
	 * Proceed field value validation
	 * 
	 * @return
	 */
	public boolean isValid();
	
	/**
	 * Set label to field
	 * @param label
	 */
	public void setLabel( String label );
	
	/**
	 * Return field label
	 * @return
	 */
	public String getLabel();
	
	/**
	 * Change value of the field
	 * @param value
	 */
	public void setValue( T value );
	
	/**
	 * Return current field value
	 * @return
	 */
	public T getValue();
	
	/**
	 * Change underlying renderer entity which responsible to provides 
	 * results after {@code IFormField::render()} method invocation.
	 * 
	 * @param renderer
	 */
	public void setRenderer( IFormFieldRenderer<?> renderer );
	
}
