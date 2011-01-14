package com.redshape.search.collectors;

import com.redshape.search.ISearchable;
import org.apache.lucene.document.Field;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:04:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IResultsCollector {

    public void collect(
        Class<? extends ISearchable> collectable,
        Field[] fields,
        String id
    );

    public <T extends ISearchable> Collection<T> getResults() throws ProcessingException;
}
