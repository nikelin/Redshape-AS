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

package com.redshape.search;

import com.redshape.search.engines.ISearchEngine;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:42:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchFacade implements ISearchFacade {
    private Map<Class<? extends ISearchEngine>, ISearchEngine> engines
			= new HashMap<Class<? extends ISearchEngine>, ISearchEngine>();

	public SearchFacade( Map<Class<? extends ISearchEngine>, ISearchEngine> engines ) {
		this.engines = engines;
	}

	@Override
	public <T extends ISearchEngine> Collection<T> getEngines() {
		return (Collection<T>) this.engines.values();
	}

	@Override
	public <T extends ISearchEngine> T getEngine(Class<T> engineClazz) {
		return (T) this.engines.get(engineClazz);
	}
}
