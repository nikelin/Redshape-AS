package com.redshape.persistence.dao.query;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.apache.log4j.Logger;

import com.redshape.persistence.dao.annotations.QueryHandler;
import com.redshape.persistence.entities.IEntity;

/**
 * Created by IntelliJ IDEA.
 * User: cwiz
 * Date: 26.11.10
 * Time: 16:20
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractQueryHolder implements IQueryHolder {
	private static final Logger log = Logger.getLogger( AbstractQueryHolder.class );
    protected IQueryBuilder qb;
    private Map<String, IQuery> queries = new HashMap<String, IQuery>();

    public AbstractQueryHolder(IQueryBuilder builder) {
        this.qb = builder;
    }

    protected IQueryBuilder getBuilder() {
        return this.qb;
    }

    @Override
    public boolean isQueryExists( String name ) {
        return this.queries.containsKey(name);
    }
    
    @Override
    public IQuery findQuery(String queryName) throws QueryBuilderException {
        if ( !this.isQueryExists( queryName ) ) {
            throw new QueryBuilderException("Query not found.");
        }

        return this.queries.get(queryName);
    }

    @Override
    public Collection<IQuery> getQueries() throws QueryBuilderException {
        return this.queries.values();
    }

    @Override
    public Collection<IQuery> getQueries(IEntity context) throws QueryBuilderException {
        try {
            Collection<IQuery> result = new HashSet<IQuery>();
            for (Method m : this.getClass().getMethods()) {
                if (m.getParameterTypes().length == 0) {
                    continue;
                }

                QueryHandler handler = m.getAnnotation(QueryHandler.class);
                if (handler == null) {
                    continue;
                }


                result.add( this.wrapQuery( (IQuery) m.invoke(this, context), handler.value() ) );
            }

            return result;
        } catch (Throwable e) {
            throw new QueryBuilderException(e.getMessage());
        }
    }

    protected IQuery wrapQuery( IQuery query, String name ) {
        return new NamedQuery( query, name );
    }

    @Override
    public void init() throws QueryBuilderException {
        try {
            for (Method m : this.getClass().getMethods()) {
                QueryHandler handler = m.getAnnotation(QueryHandler.class);
                if (handler == null) {
                    continue;
                }

                if (m.getParameterTypes().length != 0) {
                    continue;
                }

                this.queries.put( handler.value(), this.wrapQuery( (IQuery) m.invoke(this), handler.value() ) );
            }
        } catch ( Throwable e ) {
        	log.error( e.getMessage(), e );
            throw new QueryBuilderException( e.getMessage(), e );
        }
    }

}
