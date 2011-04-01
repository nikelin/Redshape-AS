package com.redshape.persistence.dao.query;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: cwiz
 * Date: 19.11.10
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public interface IQueryHolder {

	public void init() throws QueryBuilderException;
	
    public boolean isQueryExists( String name );

    public Collection<IQuery> getQueries() throws QueryBuilderException;

    public Collection<IQuery> getQueries(com.redshape.persistence.entities.IEntity entity) throws QueryBuilderException;

    public IQuery findQuery(String queryName) throws QueryBuilderException;

}
