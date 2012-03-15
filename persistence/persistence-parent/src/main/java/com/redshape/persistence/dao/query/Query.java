package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.IJoinStatement;
import com.redshape.persistence.dao.query.statements.IStatement;
import com.redshape.persistence.dao.query.statements.JoinStatement;
import com.redshape.persistence.entities.IEntity;

import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @author Surovtsev [cwiz] Sergey <cyber.wizard@gmail.com>
 */
class Query implements IQuery {
    public enum Type {
        SELECT,
        UPDATE,
        REMOVE,
        CREATE,
        STATIC,
        COUNT
    }

    private IStatement orderField;
    private OrderDirection orderDirection;
    
    private IEntity entity;
    private IExpression expression;
    private String name;
    private List<IStatement> fields = new ArrayList<IStatement>();
    private List<IStatement> groupByFields = new ArrayList<IStatement>();
    private List<IJoinStatement> joinStatements = new ArrayList<IJoinStatement>();
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private int offset = -1;
    private Type type;
    private int limit = -1;
    private Class<? extends IEntity> entityClass;

    protected Query( Type type, String name ) {
        super();
        this.type = type;
        this.name = name;
    }
    
    protected Query( Type type, Class<? extends IEntity> entityClass ) {
        super();
        this.type = type;
        this.entityClass = entityClass;
    }
    
    protected Query(Query query) {
        super();

        this.type = query.type;
        this.entityClass = query.entityClass;
        this.attributes = query.attributes;
        this.expression = query.expression;
        this.orderDirection = query.orderDirection;
        this.orderField = query.orderField;
        this.entity = query.entity();
        this.offset = query.offset;
        this.limit = query.limit;
        this.name = query.name;
        this.groupByFields = query.groupByFields;
    }

    @Override
    public List<IJoinStatement> joins() {
        return this.joinStatements;
    }

    @Override
    public IQuery join(IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType, String name) {
        this.joinStatements.add( new JoinStatement(entityType, joinType, name) );
        return this;
    }

    public IQuery where( IExpression expression ) {
        this.expression = expression;
        return this;
    }

    @Override
    public IQuery setOffset( int offset ) {
        this.offset = offset;
        return this;
    }

    @Override
    public int getOffset() {
        return this.offset;
    }

    @Override
    public int getLimit() {
        return this.limit;
    }

    @Override
    public IQuery setLimit( int limit ) {
        this.limit = limit;
        return this;
    }

    @Override
    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends IExpression> T getExpression() {
        return (T) this.expression;
    }

    @Override
    public boolean isStatic() {
        return this.type.equals(Type.STATIC);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public IQuery setAttribute(String key, Object value) {
        this.attributes.put(key, value);
        return this;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> Map<String, T> getAttributes() {
    	return (Map<String, T>) this.attributes;
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T> T getAttribute(String name) throws QueryExecutorException {
        if ( !this.attributes.containsKey(name)) {
            throw new QueryExecutorException("No such attribute: " + name);
        }

        return (T) this.attributes.get(name);
    }

    @SuppressWarnings("unchecked")
	@Override
    public <T extends IEntity> Class<T> getEntityClass() {
        return (Class<T>) this.entityClass;
    }

    @Override
    public List<IStatement> select() {
        return this.fields;
    }
    
    @Override
    public IQuery select(IStatement... statements) {
        this.fields.clear();
        this.fields.addAll( Arrays.asList(statements) );
        return this;
    }

    @Override
    public IQuery orderBy(IStatement field, OrderDirection direction ) {
        this.orderField = field;
        this.orderDirection = direction;
        return this;
    }
    
    @Override
    public OrderDirection orderDirection() {
        return this.orderDirection;
    }

    @Override
    public IStatement orderField() {
        return this.orderField;
    }

    @Override
    public List<IStatement> groupBy() {
        return this.groupByFields;
    }

    @Override
    public IQuery groupBy(IStatement... statements) {
        this.groupByFields.clear();
        this.groupByFields.addAll(Arrays.asList(statements));
        return this;
    }

    @Override
    public boolean isUpdate() {
        return this.type.equals( Type.UPDATE );
    }

    @Override
    public boolean isRemove() {
        return this.type.equals( Type.REMOVE );
    }

    @Override
    public boolean isCreate() {
        return this.type.equals( Type.CREATE );
    }
    
    @Override
    public boolean isNative() {
        return this.type.equals( Type.SELECT ) && this.type.name() != null
                && this.entityClass == null;
    }

    @Override
    public boolean isCount() {
        return this.type.equals( Type.COUNT );
    }

    @Override
    public IQuery setAttributes(Map<String, Object> attributes) {
        this.attributes = new HashMap<String, Object>( attributes );
        return this;
    }

    @Override
    public IEntity entity() {
        return this.entity;
    }

    @Override
    public IQuery entity(IEntity entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public IQuery duplicate() {
        return new Query(this);
    }

    public static IQuery createStatic( Class<? extends IEntity> type ) {
        return new Query(Type.STATIC, type);
    }
    
    public static IQuery createUpdate( Class<? extends IEntity> type ) {
        return new Query(Type.UPDATE, type);
    }
    
    public static IQuery createRemove( Class<? extends IEntity> type ) {
        return new Query(Type.REMOVE, type);
    }
    
    public static IQuery createSelect( Class<? extends IEntity> type ) {
        return new Query(Type.SELECT, type);
    }
    
    public static IQuery createNative( String name ) {
        return new Query(Type.SELECT, name);
    }
    
    public static IQuery createCountQuery( Class<? extends IEntity> type ) {
        return new Query(Type.COUNT, type );
    }
}
