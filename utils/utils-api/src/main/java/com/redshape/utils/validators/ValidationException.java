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

package com.redshape.utils.validators;

import com.redshape.utils.validators.result.IValidationResult;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/22/12
 * Time: 11:54 AM
 * To change this template use File | Settings | File Templates.
 */
public class ValidationException extends Exception {

    private IValidationResult result;

    public ValidationException(IValidationResult result) {
        this(result.getMessage());

        this.result = result;
    }

    public ValidationException() {
        this( (String) null );
    }

    public ValidationException(String message) {
        this(message, null);
    }

    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String toString() {
        return "ValidationException{result=" + result + "}";
    }
}
