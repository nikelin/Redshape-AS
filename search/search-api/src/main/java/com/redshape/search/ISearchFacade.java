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
