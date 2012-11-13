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

package com.redshape.search.index.generators;

import com.redshape.persistence.entities.IEntity;
import com.redshape.search.index.IIndexObjectIdGenerator;

import java.util.Date;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.index.generators
 * @date 9/5/11 3:28 PM
 */
public class StandardIndexObjectIdGenerator implements IIndexObjectIdGenerator {

	@Override
	public <T> T generate(Object entity) {
		T result;
		if ( entity instanceof IEntity ) {
			result = (T) (( IEntity ) entity).getId();
		} else {
			result = (T) Long.valueOf( new Date().getTime() );
		}

		return result;
	}

}
