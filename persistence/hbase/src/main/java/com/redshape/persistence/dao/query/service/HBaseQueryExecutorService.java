package com.redshape.persistence.dao.query.service;

import com.redshape.persistence.dao.*;
import com.redshape.persistence.dao.annotations.PrimaryKey;
import com.redshape.persistence.dao.annotations.QueryHolder;
import com.redshape.persistence.dao.query.*;
import com.redshape.persistence.dao.query.executors.IExecutorResult;
import com.redshape.persistence.dao.query.executors.services.IQueryExecutorService;
import com.redshape.persistence.dao.utils.IHBaseCountingManager;
import com.redshape.persistence.dao.utils.IHBaseTableManager;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.Commons;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.CompareFilter;
import org.apache.hadoop.hbase.filter.Filter;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import java.beans.IntrospectionException;
import java.io.IOException;
import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.dao.query
 * @date 1/24/12 {5:30 PM}
 */
public class HBaseQueryExecutorService implements IQueryExecutorService { 
    
    public static final String COLUMNS_FAMILY_ID = "columns_family_id";
    public static final String SERIALIZED_OBJECT_FIELD_ID = "serialized_object_data";
    
    public class ExecutionResult<T extends IEntity> implements IExecutorResult<T> {
        private List<? extends Object> results = new ArrayList<Object>();

        public ExecutionResult( Object object ) {
            if ( object == null ) {
                return;
            }

            if ( object instanceof Collection ) {
                this.results.addAll( (Collection) object );
            } else {
                this.results = Commons.list(object);
            }
        }

        public ExecutionResult(List<?> results) {
            this.results = (List<Object>) results;
        }

        @Override
        public <Z> List<Z> getValuesList() {
            return (List<Z>) this.results;
        }

        @Override
        public <Z> Z getSingleValue() {
            return (Z) Commons.firstOrNull(this.results);
        }

        @Override
        public List<T> getResultsList() {
            return (List<T>) this.results;
        }

        @Override
        public T getSingleResult() {
            return (T) Commons.firstOrNull( this.results );
        }

        @Override
        public int count() {
            return this.results.size();
        }
    }

    private static final Logger log = Logger.getLogger(HBaseQueryExecutorService.class);

    private static Collection<String> resources = new HashSet<String>();
    private static Configuration configuration = HBaseConfiguration.create();

    private byte[] columnsFamily;
    private byte[] serializedObjectFieldId;
    private IHBaseTableManager tablesManager;
    private IHBaseCountingManager countingManager;
    private IIndexBuilder indexBuilder;
    private ISerializer entitySerializer;
    private ISerializer fieldsSerializer;

    public HBaseQueryExecutorService( IHBaseTableManager tablesManager,
                                      IHBaseCountingManager countingManager,
                                      ISerializer entitiesSerializer,
                                      ISerializer fieldsSerializer,
                                      IIndexBuilder indexBuilder )
            throws SerializationException {
        this.tablesManager = tablesManager;
        this.countingManager = countingManager;
        this.entitySerializer = entitiesSerializer;
        this.fieldsSerializer = fieldsSerializer;
        this.indexBuilder = indexBuilder;
        this.checkFields();
        this.init();
    }

    protected void init() throws SerializationException {
        this.columnsFamily = this.getFieldsSerializer().serialize(COLUMNS_FAMILY_ID);
        this.serializedObjectFieldId = this.getFieldsSerializer().serialize(SERIALIZED_OBJECT_FIELD_ID);
    }

    protected void checkFields() {
        if ( this.tablesManager == null ) {
            throw new IllegalStateException("<null>: tables manager must be provided");
        }

        if ( this.countingManager == null ) {
            throw new IllegalStateException("<null>: counting manager must be provided");
        }

        if ( this.entitySerializer == null ) {
            throw new IllegalStateException("<null>: entities serializer must be provided");
        }

        if ( this.fieldsSerializer == null ) {
            throw new IllegalStateException("<null>: fields serializer must be provided");
        }

        if ( this.indexBuilder == null ) {
            throw new IllegalStateException("<null>: indexes builder must be provided");
        }
    }

    protected IHBaseTableManager getTablesManager() {
        return tablesManager;
    }

    protected ISerializer getFieldsSerializer() {
        return this.fieldsSerializer;
    }

    public IIndexBuilder getIndexBuilder() {
        return indexBuilder;
    }

    protected IHBaseCountingManager getCountingManager() {
        return countingManager;
    }

    protected HTable getTable( Class<? extends IEntity> entityClazz ) throws DAOException {
        return this.getTablesManager().forEntity(entityClazz);
    }

    protected byte[] getSerializedObjectFieldId() {
        return this.serializedObjectFieldId;
    }

    public ISerializer getEntitySerializer() {
        return entitySerializer;
    }

    protected byte[] getColumnsFamily() throws DAOException {
        return this.columnsFamily;
    }

    @Override
    @Transactional
    public <T extends IEntity> IExecutorResult<T> execute(IQuery query) throws DAOException {
        try {
            int offset = query.getOffset() > 0 ? query.getOffset() : -1;
            int limit = query.getLimit() > 0 ? query.getLimit() : -1;

            Object value = null;
            if ( this.isUpdateQuery(query) ) {
                value = this.executeUpdate(query);
            } else if ( query.isNative() ) {
                value = this.executeNamedQuery( query.getEntityClass(),
                        query.getName(),
                        query.getAttributes(),
                        offset, limit );
            } else {
                if ( query.isStatic() ) {
                    value = this.executeStatic(query, offset, limit);
                } else {
                    value = this.executeDynamic(query, offset, limit);
                }
            }

            return new ExecutionResult<T>(value);
        } catch ( QueryExecutorException e ) {
            throw new DAOException( e.getMessage(), e );
        } catch ( SerializationException e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    protected <T extends IEntity> T executeSave(IQuery query) throws DAOException {
        this.executeSave( query, Arrays.asList( ( T[] ) new IEntity[] { query.entity() } ) );
        return (T) query.entity();
    }

    protected void executeSave( IQuery query, Collection<? extends IEntity> entities) throws DAOException {
        for ( IEntity entity : entities ) {
            this.executeSave( query, entity, query.isCreate() );
        }
    }

    private void executeSave( IQuery query, IEntity entity, boolean createMode ) throws DAOException {
        try {
            if ( createMode ) {
                if (this.findById(entity.getClass(), entity.getId()) == null) {
                    countingManager.increaseCount(entity.getClass());
                }
            }

            this.getTable(entity.getClass())
                .put(this.getPut(entity));
        } catch ( Throwable e) {
            throw new DAOException(e.getMessage(), e);
        }
    }

    protected <T extends IEntity> T findById(Class<T> entityClass, Long id) throws DAOException {
        try {
            Get g = new Get(this.getFieldsSerializer().serialize(id));
            Result result = this.getTable(entityClass).get(g);

            if (result.isEmpty()) {
                return null;
            }


            byte[] value = result.getValue(
                this.getColumnsFamily(),
                this.getSerializedObjectFieldId()
            );

            return this.getEntitySerializer().deserealize(value, entityClass);
        } catch (SerializationException e) {
            throw new DAOException(e.getMessage(), e);
        } catch ( IOException e ) {
            throw new DAOException("I/O related exception", e );
        }
    }

    @Transactional
    protected <T extends IEntity> T executeUpdate(IQuery query) throws DAOException {
        T result = null;
        if ( query.isRemove() ) {
            if ( query.entity() != null ) {
                result = this.executeRemove(query);
            } else {
                result = this.executeRemoveAll(query);
            }
        } else if ( query.isUpdate() || query.isCreate() ) {
            result = this.executeSave(query);
        }

        return result;
    }

    @Transactional
    protected <T extends IEntity> T executeRemoveAll( IQuery query ) throws DAOException {
        HTable table = this.getTablesManager().forEntity(query.getEntityClass());
        this.getTablesManager().disable( table );
        this.getTablesManager().delete( table );

        this.getCountingManager().reset(query.getEntityClass());
        this.getTablesManager().forEntity( query.getEntityClass() );

        return null;
    }

    @Transactional
    protected <T extends IEntity> T executeRemove( IQuery query ) throws DAOException {
        T entity = (T) query.entity();
        if ( entity == null ) {
            throw new IllegalArgumentException("<null>");
        }

        Delete delete;
        try {
            delete = new Delete(this.getFieldsSerializer().serialize(query.entity().getId()));
        } catch (SerializationException e) {
            throw new DAOException(e.getMessage(), e);
        }

        try {
            this.getTable(query.getEntityClass()).delete(delete);
            this.getCountingManager().decreaseCount( query.getEntityClass() );
        } catch (IOException e) {
            throw new DAOException(e.getMessage(), e);
        }

        return entity;
    }

    protected boolean isUpdateQuery( IQuery query ) {
        return query.isCreate() || query.isUpdate()
                || query.isRemove();
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                                     String queryName,
                                                                     Map<String, Object> params )
            throws DAOException {
        return this.executeNamedQuery(entityClazz, queryName, params, -1 );
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                                     String queryName,
                                                                     Map<String, Object> params,
                                                                     int offset )
            throws DAOException {
        return this.executeNamedQuery(entityClazz, queryName, params, offset, -1 );
    }

    @Override
    public <T extends IEntity> IExecutorResult<T> executeNamedQuery( Class<? extends IEntity> entityClazz,
                                                                     String queryName,
                                                                     Map<String, Object> params,
                                                                     int offset,
                                                                     int limit )
            throws DAOException {
        IQueryHolder queryHolder = this.getQueryHolder(entityClazz);
        if ( queryHolder == null ) {
            throw new IllegalArgumentException("There is no query holder" +
                    " related to the provided provided");
        }

        if ( !queryHolder.isQueryExists(queryName) ) {
            throw new IllegalArgumentException("Requested query not exists withing target holder!");
        }

        try {
            IQuery query = queryHolder.findQuery(queryName).duplicate();
            query.setOffset(offset);
            query.setLimit(limit);
            query.setAttributes(params);

            return this.execute( query );
        } catch ( QueryBuilderException e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    private Long processPrimaryKey(IEntity entity) throws DAOException, SerializationException {
        try {
            PrimaryKey annotation = entity.getClass().getAnnotation(PrimaryKey.class);
            if (annotation != null) {
                PropertyUtils propertyUtils = new PropertyUtils();
                String fieldName = annotation.value();

                Property property = propertyUtils.getProperty(entity.getClass(), fieldName);
                Object value = property.get(entity);

                Filter filter = new SingleColumnValueFilter(
                        this.getFieldsSerializer().serialize( this.columnsFamily ),
                        this.getFieldsSerializer().serialize(fieldName),
                        CompareFilter.CompareOp.EQUAL,
                        this.getFieldsSerializer().serialize(value)
                );

                IEntity targetEntity = Commons.firstOrNull( this.executeFilter( entity.getClass(), filter, 0, 1) );
                if ( targetEntity != null ) {
                    return entity.getId();
                }
            }

            return entity.getId();
        } catch ( IntrospectionException e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    protected List<Put> getPut( Collection<IEntity> entities ) throws DAOException {
        List<Put> result = new ArrayList<Put>();
        for ( IEntity entity : entities ) {
            result.add( this.getPut(entity) );
        }

        return result;
    }

    protected Put getPut( IEntity entity ) throws DAOException {
        try {
            byte[] serializedObject = this.getEntitySerializer().serialize(entity);
            byte[] rowId = this.getFieldsSerializer().serialize( entity.getId() );

            Put put = new Put( rowId );
            put.add(this.getColumnsFamily(),
                    this.getFieldsSerializer().serialize("serialized_object"),
                    serializedObject);


            Map<String, byte[]> indexFields = this.getIndexBuilder().buildIndex(entity);
            // Index fields
            for (String fieldName : indexFields.keySet()) {
                put.add(
                        this.getColumnsFamily(),
                        this.getFieldsSerializer().serialize(fieldName),
                        indexFields.get(fieldName)
                );
            }

            return put;
        } catch ( SerializationException e ) {
            throw new DAOException("Unable to marshall entity object", e );
        } catch ( IndexBuilderException e ) {
            throw new DAOException("Index construction failed", e );
        }
    }

    protected byte[] getRowId( IEntity entity ) throws DAOException {
        byte[] rowId;
        try {
            rowId = this.getFieldsSerializer().serialize(entity.getId());
        } catch (SerializationException e) {
            throw new DAOException(e.getMessage());
        }

        return rowId;
    }

    protected <T extends IEntity> List<T> executeFilter(Class<? extends IEntity> entityClazz,
                                                        Filter filter, int offset, int limit)
            throws DAOException {
        List<T> resultList = new ArrayList<T>();

        Scan scan = new Scan();
        if (filter != null) {
            scan.setFilter(filter);
        }

        ResultScanner scanner = null;
        try {
            scanner = this.getTable(entityClazz).getScanner(scan);

            int currentOffset = 0,
                    currentRecords = 0;
            try {
                for (Result result : scanner) {
                    if (offset > 0 && currentOffset++ < offset) continue;
                    if (limit > 0 && currentRecords++ >= limit) break;


                    /**
                     * @FIXME: What the fucking shit is this?
                     */
                    byte[] value = result.getValue(
                            this.columnsFamily,
                            this.getFieldsSerializer().serialize("serialized_object")
                    );

                    resultList.add( (T)  this.getEntitySerializer().deserealize(value, entityClazz) );
                }
            } catch ( SerializationException e ) {
                throw new DAOException( e.getMessage(), e );
            } finally {
                scanner.close();
            }
        } catch ( IOException e ) {
            throw new DAOException("Unable to create scanner instance", e );
        } finally {
            if ( scanner != null ) {
                scanner.close();
            }
        }

        return resultList;
    }

    protected <T extends IEntity> List<T> getStaticQueryResult( Class<? extends IEntity> entityClazz,
                                                                String queryName,
                                                                Boolean value,
                                                                int offset, int limit)
            throws DAOException, SerializationException {
        SingleColumnValueFilter filter = new SingleColumnValueFilter(
                this.getColumnsFamily(),
                this.getFieldsSerializer().serialize(queryName),
                CompareFilter.CompareOp.EQUAL,
                this.getFieldsSerializer().serialize(value)
        );

        return this.executeFilter(entityClazz, filter, offset, limit);
    }


    protected <T extends IEntity> List<T> executeStatic( IQuery query, int offset, int limit )
            throws DAOException, SerializationException, QueryExecutorException {
        return this.getStaticQueryResult(query.getEntityClass(),
                query.getName(), true, offset, limit);
    }

    protected <T extends IEntity> List<T> executeDynamic( IQuery query, int offset, int limit  )
            throws DAOException, SerializationException, QueryExecutorException {
        Filter filter = this.getDynamicQueryExecutor(query)
                .execute();

        return this.executeFilter( query.getEntityClass(), filter, offset, limit);
    }

    protected IQueryHolder getQueryHolder( Class<? extends IEntity> entityClazz ) throws DAOException {
        QueryHolder holderMeta = entityClazz.getAnnotation(QueryHolder.class);
        if ( holderMeta == null ) {
            return null;
        }

        Class<? extends IQueryHolder> holderClazz = holderMeta.value();

        IQueryHolder concreteHolder;
        try {
            concreteHolder = holderClazz.newInstance();
            concreteHolder.init();
        } catch (Exception e) {
            throw new DAOException(e.getMessage(), e);
        }

        return concreteHolder;
    }

    protected HBaseQueryExecutor getDynamicQueryExecutor(IQuery query) throws SerializationException {
        return new HBaseQueryExecutor(query, this.getFieldsSerializer());
    }

    protected static String getEntityName(IQuery query) {
        return query.getEntityClass().getSimpleName();
    }
}
