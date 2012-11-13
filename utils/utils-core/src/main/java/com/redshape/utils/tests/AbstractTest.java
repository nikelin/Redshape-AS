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

package com.redshape.utils.tests;

import junit.framework.TestCase;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTest<V> extends TestCase {
	private Map<V, Object> attributes = new HashMap<V, Object>();
	
	protected void setAttribute( V key, Object value ) {
		this.attributes.put(key, value);
	} 
	
	@SuppressWarnings("unchecked")
	protected <T> T getAttribute( V key ) {
		return (T) this.attributes.get(key);
	}
	
}
