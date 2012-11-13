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

package com.redshape.ui.data;

/**
 * Data model events
 * 
 * @author root
 */
public class ModelEvent extends DataEvents {

	protected ModelEvent(String code) {
		super(code);
	}

	/**
	 * Raises by data models when some of their fields going to be changed 
	 */
	public static final ModelEvent CHANGED = new ModelEvent("DataEvents.ModelEvent.CHANGED");

	/**
	 * Raises by data modes to inform store about need to remove them
	 */
	public static final ModelEvent REMOVED = new ModelEvent("DataEvents.ModelEvent.REMOVED");

}
