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

package com.redshape.search.query.transformers;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.query.transformers
 * @date 9/5/11 3:25 PM
 */
public class StandardTransformersBuilder implements ITransformersBuilder {
	private Map<Class<? extends IQueryTransformer>, IQueryTransformer> transformers
			= new HashMap<Class<? extends IQueryTransformer>, IQueryTransformer>();

	public StandardTransformersBuilder( Map<Class<? extends IQueryTransformer>,
								 IQueryTransformer> transformers ) {
		this.transformers = transformers;
	}

	@Override
	public <T extends IQueryTransformer> T getTransformer(Class<? extends T> clazz) {
		return (T) transformers.get( clazz );
	}
}
