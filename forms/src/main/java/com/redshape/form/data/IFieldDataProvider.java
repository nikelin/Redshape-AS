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

package com.redshape.form.data;

import java.util.Map;

/**
 * Provides ability to implement invalidatable data fields
 * which will be able to synchronize self state with some
 * persistent data source or elsewhere.
 *
 * Can be used as anonymous interface to provide in each
 * field instance.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.form.data
 * @date 9/9/11 3:37 PM
 */
public interface IFieldDataProvider<T> {

	/**
	 * Invalidate current provider state to check
	 * that data is in actual state.
	 *
	 * @return
	 */
	public boolean invalidate();

	/**
	 * Provide collection of values
	 *
	 * @return
	 */
	public Map<String, T> provideCollection();

	/**
	 * Provide single value
	 *
	 * @return
	 */
	public T provide();

}
