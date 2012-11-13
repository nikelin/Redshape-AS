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

package com.redshape.search.query.terms;

/**
 * Represents set of available operation might be applied
 * to a some of available search terms.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public enum Operation {
	/**
	* @see com.redshape.search.query.terms.AndTerm
	*/
    AND,
	/**
	 * @see com.redshape.search.query.terms.OrTerm
	 */
    OR,
	/**
	 * @see com.redshape.search.query.terms.NotTerm
	 */
    NOT,
	/**
	 * @see com.redshape.search.query.terms.EqTerm
	 */
    EQUALS,
	/**
	 * @see com.redshape.search.query.terms.ToTerm
	 */
	TO
}
