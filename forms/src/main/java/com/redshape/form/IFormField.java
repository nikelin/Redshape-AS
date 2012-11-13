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

package com.redshape.form;

import com.redshape.utils.validators.IValidator;
import com.redshape.utils.validators.result.IValidationResult;

import java.util.Collection;
import java.util.List;

/**
 * Interface to represent form field such as textfield, select, etc.
 *
 * @author nikelin
 *
 * @param <T>
 */
public interface IFormField<T> extends IFormItem {

    /**
     * Always return true, if field can handle more than
     * one value at same time
     * @return
     */
    public boolean hasMultiValues();
    
    /**
     * Return all selected values related to a field
     * @return
     */
    public List<T> getValues();

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
    public <V extends IValidator<T, IValidationResult>> void addValidator( V validator );

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

}
