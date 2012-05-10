package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IAliasStatement;
import com.redshape.persistence.dao.query.statements.IJoinStatement;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.entities.IEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Data source query object
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 */
public interface IQuery<Z> extends Serializable {

    public boolean hasAttribute( String name );

    public <T extends IExpression> T getExpression();

    public String getName();

    public IQuery<Z> setAttribute(String name, Object value);

    public <T> T getAttribute(String name) throws QueryExecutorException;
    
    public IQuery setAttributes( Map<String, Object> attributes );

    public void alias( IStatement source, String target );

    public List<IAliasStatement> aliases();

    /**
     * Return attributes which applyed on this query object.
     *
     * @param <T>
     * @return
     */
    public <T> Map<String, T> getAttributes();

    /**
     * Return current querying results offset
     * @return
     */
    public int getOffset();

    /**
     * Apply offset on query results
     * @param offset
     * @return
     */
    public IQuery<Z> setOffset( int offset );

    /**
     * Returns value of maximum results size
     * constraint.
     *
     * @return
     */
    public int getLimit();

    /**
     * Return all joined associations
     * @return
     */
    public List<IJoinStatement> joins();

    /**
     *
     * @param entityType
     * @param joinType
     * @param name
     * @return
     */
    public IQuery<Z> join( IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType,  String name );

    public IQuery<Z> join( IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType,  String name,
                        String alias );


    /**
     * Return list of fields which requested to be include
     * in each record from the querying results list.
     *
     * @return
     */
    public List<IStatement> select();

    /**
     * Accepts list of field references which should be
     * included in execution result
     *
     * @param statements
     * @return
     */
    public IQuery<Z> select( IStatement... statements );

    /**
     * Return direction of results ordering
     * @return
     */
    public OrderDirection orderDirection();

    /**
     * Return ordering point (field)
     * @return
     */
    public IStatement orderField();

    /**
     * Change querying result ordering point and direction
     * @param field
     * @param direction
     * @return
     */
    public IQuery<Z> orderBy(IStatement field, OrderDirection direction );

    /**
     * Return list of fields which is used to arrange querying results
     * on groups.
     *
     * @return
     */
    public List<IStatement> groupBy();

    /**
     * Accepts list of field references which will
     * be used to group querying result.
     * @param statements
     * @return
     */
    public IQuery<Z> groupBy( IStatement... statements );

    /**
     * Bind conditional expression to this query object
     * which will be used as a filter applied to records
     * from data source to make decision of adopting them
     * to the querying results.
     *
     * @param expression
     * @return
     */
    public IQuery<Z> where( IExpression expression );

    /**
     * Apply maximum size constraint on querying results
     * list.
     * @param limit
     * @return
     */
    public IQuery<Z> setLimit( int limit );

    /**
     * Returns entities class which relates to this query
     * @param <T>
     * @return
     */
    public Class<Z> getEntityClass();

    public void setEntityClass( Class<Z> entityClass );

    public Z entity();

    /**
     * Binds some entity to this query.
     *
     * This method must be used when query objects is used
     * to save, update or remove some entity from data source.
     *
     * @param entity
     * @return
     */
    public IQuery<Z> entity( Z entity );

    /**
     * Returns true if this query should be treated as a holder
     * of named query which must be executed instead.
     *
     * @return
     */
    public boolean isNative();

    /**
     * Returns true if this query goal is to
     * update existing record in data source.
     *
     * @return
     */
    public boolean isUpdate();

    /**
     * Returns true if this is boolean query which must be executed
     * on a client side instead of invocation server-side executor.
     *
     * @important Create, remove and update queries must be always have entity which is bound
     * on them; elsewhere there is QueryExecutorException will be thrown by
     * IQueryExecutor on attempt to process such query by execute(IQuery) of
     * some target dao.
     *
     * @return
     */
    public boolean isStatic();

    /**
     * Returns true if this query goal is to remove
     * some record from the data source.
     *
     * @important Create, remove and update queries must be always have entity which is bound
     * on them; elsewhere there is QueryExecutorException will be thrown by
     * IQueryExecutor on attempt to process such query by execute(IQuery) of
     * some target dao.
     *
     * @return
     */
    public boolean isRemove();

    /**
     * Returns true if this query goal is to save
     * some record information in remote data source.
     *
     * @important Create, remove and update queries must be always have entity which is bound
     * on them; elsewhere there is QueryExecutorException will be thrown by
     * IQueryExecutor on attempt to process such query by execute(IQuery) of
     * some target dao.
     *
     * @return
     */
    public boolean isCreate();

    public boolean isCount();

    /**
     * Clone this query object and return copied one.
     *
     * @return
     */
    public IQuery<Z> duplicate();

}

