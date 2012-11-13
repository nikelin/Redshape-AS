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
 * Represents binary search term.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public interface IBinaryTerm extends ISearchTerm {

	/**
	 * Return left member of a binary group
	 * @return
	 */
    public <T extends ISearchTerm> T getLeft();

	/**
	 * Return right member of a binary group
	 * @return
	 */
    public <T extends ISearchTerm> T getRight();

	/**
	 * Return operation which join binary group
	 * @return
	 */
	public Operation getOperation();

}
