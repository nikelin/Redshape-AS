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

package com.redshape.form.fields;

public class InputField extends AbstractField<String> {
	private static final long serialVersionUID = -6803108853561548397L;

	public enum Type {
		TEXT,
		PASSWORD,
		HIDDEN,
		FILE,
		SUBMIT,
		RESET
	}
	
	private InputField.Type type;
	
	public InputField( InputField.Type type ) {
		this( type, null );
	}
	
	public InputField( InputField.Type type, String id ) {
		this( type, id, id);
	}
	
	public InputField( InputField.Type type, String id, String name ) {
		super(id, name);
		
		this.type = type;
	}
	
	public InputField.Type getType() {
		return this.type;
	}

    @Override
    public void resetState() {
        if ( this.getType().equals(InputField.Type.SUBMIT ) ) {
            return;
        }

        super.resetState();
    }

}
