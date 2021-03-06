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
 * Represents group of single field clauses as described
 * in  {@link ##http://lucene.apache.org/java/2_4_0/queryparsersyntax.html## }
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.terms
 * @date 9/7/11 1:55 PM
 */
public interface IGroupingTerm extends ISearchTerm {

	/**
	 * List field terms which added to this group
	 *
	 * @return
	 */
	public ISearchTerm[] getList();

	public Operation getOperation();

}
