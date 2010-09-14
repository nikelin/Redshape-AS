package com.redshape.persistence.entities.tree.fetchers;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Mar 3, 2010
 * Time: 3:31:14 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FetcherInterface<T> {

    public T fetchParent() throws FetcherException;

    public List<T> fetchChildren() throws FetcherException;

    public T fetchNode( Integer left, Integer right ) throws FetcherException;

    public T fetchLastNode() throws FetcherException;
}
