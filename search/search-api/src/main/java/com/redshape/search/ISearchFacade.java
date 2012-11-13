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

/**
 * Search engines facade
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search
 * @date 9/5/11 2:04 PM
 */
public interface ISearchFacade {

    /**
     * Create if not exists new instanceof of search engine
     * by a class given as engineClazz parameter
     *
     * @param engineClazz
     * @param <T>
     * @return
     */
    public <T extends ISearchEngine> T getEngine( Class<T> engineClazz );

    /**
     * Return all registered search engines instances
     *
     * @return
     */
    public <T extends ISearchEngine> Collection<T> getEngines();

}
