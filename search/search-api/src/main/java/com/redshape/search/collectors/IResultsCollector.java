package com.redshape.search.collectors;

import com.redshape.search.index.IIndexField;

import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:04:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IResultsCollector {

    public void collect( Class<?> collectable, Map<IIndexField, Object> fields, String id )
			throws ProcessingException;

    public <T> Collection<T> getResults() throws ProcessingException;
}
