package com.redshape.persistence.dao;

import com.redshape.persistence.dao.annotations.Index;
import com.redshape.persistence.dao.annotations.IndexField;
import com.redshape.persistence.dao.annotations.QueryHolder;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.dao.query.IQueryHolder;
import com.redshape.persistence.dao.query.executors.IStaticQueryExecutor;
import com.redshape.persistence.dao.query.executors.StaticQueryExecutor;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 22.11.10 20:10
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @author Sergey Surovtsev <cyber.wizard@gmail.com>
 */
public class HBaseIndexBuilder implements IIndexBuilder {
    private static final Logger log = Logger.getLogger(HBaseIndexBuilder.class);
    private Class<? extends IEntity> entityClass;
    private ISerializer serializer;

    public <T extends IEntity> HBaseIndexBuilder(Class<T> entityClass) throws DAOException {
        this.entityClass = entityClass;
    }

    public <T extends IEntity> HBaseIndexBuilder() {
    }

    private Map<String, byte[]> buildFieldsIndex(IEntity entity) throws IntrospectionException, SerializationException {
        PropertyUtils propertyUtils = new PropertyUtils();
        HashMap<String, byte[]> returns = new HashMap<String, byte[]>();

        Index index = this.getEntityClass().getAnnotation(Index.class);

        if (index == null) {
            return returns;
        }

        IndexField[] fields = index.value();

        for (IndexField field : fields) {
            Property property = propertyUtils.getProperty(this.getEntityClass(), field.value());
            Object value = property.get(entity);

            returns.put(field.value(), value != null ? this.getSerializer().serialize(value) : null );
        }

        return returns;
    }

    protected Map<String, byte[]> buildQueriesIndex(IEntity entity) throws IndexBuilderException {

        // index fields
        Map<String, byte[]> returns = new HashMap<String, byte[]>();

        QueryHolder holderMeta = this.getEntityClass().getAnnotation(QueryHolder.class);
        if (holderMeta == null) {
            return returns;
        }

        Class<? extends IQueryHolder> queryHolder = holderMeta.value();
        if (queryHolder == null) {
            return returns;
        }

        IQueryHolder concreteHolder;
        try {
            concreteHolder = queryHolder.newInstance();
        } catch (Exception e) {
            throw new IndexBuilderException(e.getMessage());
        }

        Collection<IQuery> queries;
        try {
            queries = concreteHolder.getQueries(entity);
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new IndexBuilderException();
        }

        for (IQuery query : queries) {
            try {
                if (!query.isStatic()) {
                    continue;
                }

                IStaticQueryExecutor<Boolean> staticQueryExecutor = this.getStaticQueryExecutor(query);
                Boolean result = staticQueryExecutor.execute();
                byte[] value = this.serializer.serialize(result);

                returns.put(query.getName(), value);
            } catch (Throwable e) {
                log.error( e.getMessage(), e );
                throw new IndexBuilderException(e.getMessage());
            }
        }

        return returns;
    }


    @Override
    public Map<String, byte[]> buildIndex(IEntity entity) throws IndexBuilderException {
        try {
            Map<String, byte[]> result = new HashMap<String, byte[]>();
            result.putAll(this.buildFieldsIndex(entity));
            result.putAll(this.buildQueriesIndex(entity));

            return result;
        } catch (Throwable e) {
            log.error( e.getMessage(), e );
            throw new IndexBuilderException(e.getMessage());
        }
    }

    @Override
    public void setSerializer(ISerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    public ISerializer getSerializer() {
        return this.serializer;
    }

    @Override
    public IStaticQueryExecutor<Boolean> getStaticQueryExecutor(IQuery query) {
        return new StaticQueryExecutor(query);
    }

    @Override
    public void setEntityClass(Class<? extends IEntity> clazz) {
        this.entityClass = clazz;
    }

    @Override
    public Class<? extends IEntity> getEntityClass() {
        return this.entityClass;
    }
}
