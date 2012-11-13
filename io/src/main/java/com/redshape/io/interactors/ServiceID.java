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

package com.redshape.io.interactors;

import com.redshape.utils.IEnum;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io.interactors
 * @date 10/22/11 10:22 PM
 */
public class ServiceID implements IEnum<String> {
	public static final String SAMBA_ID = "SAMBA";
	public static final String SSH_ID = "SSH";

	private String name;

	protected ServiceID( String name ) {
		this.name = name;
	}

	@Override
	public String name() {
		return this.name;
	}

	public static final ServiceID SAMBA = new ServiceID(SAMBA_ID);
	public static final ServiceID SSH = new ServiceID(SSH_ID);
	public static final ServiceID NONE = new ServiceID("ServiceID.Unknown");

	@Override
	public String toString() {
		return this.name();
	}

	@Override
	public int hashCode() {
		return this.name().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if ( obj == null || !( obj instanceof ServiceID) ) {
			return false;
		}

		return (( ServiceID ) obj).name().equals( this.name() );
	}
}
