package com.redshape.search.engines;

import com.redshape.search.query.terms.ISearchTerm;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 3:50:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISearchEngine {

    public <T> Collection<T> find( Class<? extends T> searchable, ISearchTerm query ) throws EngineException;

    public void save( Object object ) throws EngineException;

    public void remove( Object object ) throws EngineException;

}
