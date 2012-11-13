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

package com.redshape.utils.validators.result;

import java.util.HashSet;

/**
 * @author nikelin
 * @date 18/04/11
 * @package com.redshape.validators.result
 */
public class ResultsList extends HashSet<IValidationResult>
						 implements IResultsList {
	private static final long serialVersionUID = 3441164189894402471L;
	
	private String name;
	private boolean success;

	public ResultsList() {
		this(true);
	}

	public ResultsList( boolean success ) {
		this( null, success );
	}

	public ResultsList( String name, boolean success ) {
		super();

        this.success = success;
		this.name = name;
	}

	@Override
	public void markValid( boolean value ) {
		this.success = value;
	}

	@Override
	public String getMessage() {
		return null;
	}
	
	public String getName() {
		return this.name;
	}

	@Override
	public boolean isValid() {
		boolean result = this.success;
		if ( !result ) {
			return result;
		}
		
		for ( IValidationResult item : this ) {
			result = result && item.isValid();
		}

		return result;
	}
}
