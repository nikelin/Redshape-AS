package com.redshape.persistence.dao.query;

import com.redshape.persistence.dao.query.expressions.IExpression;
import com.redshape.persistence.dao.query.statements.*;

import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @author Surovtsev [cwiz] Sergey <cyber.wizard@gmail.com>
 */
class Query<Z> implements IQuery<Z> {
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

    private Z entity;
    private IExpression expression;
    private String name;
    private List<IAliasStatement> aliasStatements = new ArrayList<IAliasStatement>();
    private List<IStatement> fields = new ArrayList<IStatement>();
    private List<IStatement> groupByFields = new ArrayList<IStatement>();
    private List<IJoinStatement> joinStatements = new ArrayList<IJoinStatement>();
    private Map<String, Object> attributes = new HashMap<String, Object>();
    private int offset = -1;
    private Type type;
    private int limit = -1;
    private Class<Z> entityClass;

    protected Query( Type type, String name ) {
        super();
        this.type = type;
        this.name = name;
    }

    protected Query( Type type, Class<? extends Z> entityClass ) {
        super();
        this.type = type;
        this.entityClass = (Class<Z>) entityClass;
    }

    protected Query(Query<Z> query) {
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
    public void alias( IStatement source, String target ) {
        this.aliasStatements.add( new AliasStatement( source, target ) );
    }

    @Override
    public List<IAliasStatement> aliases() {
        return this.aliasStatements;
    }

    @Override
    public List<IJoinStatement> joins() {
        return this.joinStatements;
    }

    @Override
    public IQuery<Z> join(IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType, String name) {
        return this.join(entityType, joinType, name, null);
    }

    @Override
    public IQuery<Z> join(IJoinStatement.JoinEntityType entityType, IJoinStatement.JoinType joinType, String name,
                          String alias) {
        if ( alias != null ) {
            this.aliasStatements.add( new AliasStatement(new ScalarStatement(name), alias, false) );
        }

        this.joinStatements.add( new JoinStatement(entityType, joinType, name, alias) );
        return this;
    }

    public IQuery<Z> where( IExpression expression ) {
        this.expression = expression;
        return this;
    }

    @Override
    public IQuery<Z> setOffset( int offset ) {
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
    public IQuery<Z> setLimit( int limit ) {
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
    public IQuery<Z> setAttribute(String key, Object value) {
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
    public Class<Z> getEntityClass() {
        return (Class<Z>) this.entityClass;
    }

    @Override
    public void setEntityClass(Class<Z> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public List<IStatement> select() {
        return this.fields;
    }

    @Override
    public IQuery<Z> select(IStatement... statements) {
        this.fields.clear();
        this.fields.addAll( Arrays.asList(statements) );
        return this;
    }

    @Override
    public IQuery<Z> orderBy(IStatement field, OrderDirection direction ) {
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
    public IQuery<Z> groupBy(IStatement... statements) {
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
    public IQuery<Z>  setAttributes(Map<String, Object> attributes) {
        this.attributes = new HashMap<String, Object>( attributes );
        return this;
    }

    @Override
    public Z entity() {
        return this.entity;
    }

    @Override
    public IQuery<Z> entity(Z entity) {
        this.entity = entity;
        return this;
    }

    @Override
    public IQuery<Z> duplicate() {
        return new Query(this);
    }

    public static <Z> IQuery<Z> createStatic( Class<? extends Z> type ) {
        return new Query(Type.STATIC, type);
    }

    public static <Z> IQuery<Z> createUpdate( Class<? extends Z> type ) {
        return new Query(Type.UPDATE, type);
    }

    public static <Z> IQuery<Z>  createRemove( Class<? extends Z> type ) {
        return new Query(Type.REMOVE, type);
    }

    public static <Z> IQuery<Z>  createSelect( Class<? extends Z> type ) {
        return new Query(Type.SELECT, type);
    }

    public static <Z> IQuery<Z>  createNative( String name ) {
        return new Query(Type.SELECT, name);
    }

    public static <Z> IQuery<Z> createCountQuery( Class<? extends Z> type ) {
        return new Query(Type.COUNT, type );
    }
}
