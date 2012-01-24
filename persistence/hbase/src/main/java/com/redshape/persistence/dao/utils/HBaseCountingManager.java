package com.redshape.persistence.dao.utils;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.ISerializer;
import com.redshape.persistence.dao.SerializationException;
import com.redshape.persistence.entities.IEntity;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.RowLock;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nikelin
 */
public class HBaseCountingManager implements IHBaseCountingManager{
    private static final Logger log = Logger.getLogger( HBaseCountingManager.class );
    private static String TABLE_NAME = "HBASE_ENTITIES_COUNTING_STATS";

    private HTable table;
    private IHBaseTableManager tableUtil;
    private ExecutorService executor;
    private ISerializer serializer;
    private byte[] columnsFamily;

    protected HBaseCountingManager( IHBaseTableManager tableUtil,
                                    ISerializer serializer )
        throws SerializationException {
        this.tableUtil = tableUtil;
        this.serializer = serializer;
        this.executor = Executors.newFixedThreadPool(10);
        this.checkFields();
        this.init();
    }
    
    protected void checkFields() {
        if ( this.tableUtil == null ) {
            throw new IllegalArgumentException("<null>: tables manager must be provided");
        }
        
        if ( serializer == null ) {
            throw new IllegalArgumentException("<null>: columns serializer must be provided");
        }
    }

    protected void init() throws SerializationException {
        this.columnsFamily = this.getSerializer().serialize("redshape");
    }

    protected ExecutorService getExecutor() {
        return this.executor;
    }

    protected IHBaseTableManager getTableUtil() {
        return tableUtil;
    }

    protected byte[] getColumnsFamily() {
        return this.columnsFamily;
    }

    protected ISerializer getSerializer() {
        return serializer;
    }

    protected HTable getTable() throws DAOException {
        if ( this.table == null ) {
            return this.table = this.getTableUtil().forName( TABLE_NAME );
        } 
        
        return this.table;
    }

    @Override
    public void increaseCount( Class<? extends IEntity> entity ) throws DAOException {
        this.increaseCount(entity, 1L);
    }

    @Override
    public void increaseCount( Class<? extends IEntity> entity, Long count ) throws DAOException {
        this.updateCount(entity, count);
    }

    @Override
    public void decreaseCount( Class<? extends IEntity> entity ) throws DAOException {
        this.decreaseCount(entity, new Long(1));
    }

    @Override
    public void decreaseCount( Class<? extends IEntity> entity, Long count ) throws DAOException {
        this.updateCount( entity, -count );
    }

    protected void updateCount( final Class<? extends IEntity> entity, final Long count ) throws DAOException {
        try {
            this.getExecutor().execute( new Thread() {
                @Override
                public void run() {
                    try {
                        HBaseCountingManager.this.actualUpdate( entity, count );
                    } catch ( DAOException e ) {
                        log.error( e.getMessage(), e );
                        throw new RuntimeException( e.getMessage(), e );
                    }
                }
            });
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new DAOException( e.getMessage(), e );
        }
    }
    
    protected void actualUpdate( Class<? extends IEntity> entity, Long count ) throws DAOException {
        try {
            RowLock lock = HBaseCountingManager.this.getTable()
                    .lockRow(
                            this.getSerializer().serialize( getEntityName(entity) ) );

            HBaseCountingManager.this.getTable().incrementColumnValue(
                    HBaseCountingManager.this.getSerializer().serialize( "count" ),
                    HBaseCountingManager.this.getSerializer().serialize( this.getColumnsFamily() ),
                    HBaseCountingManager.this.getSerializer().serialize( getEntityName(entity) ),
                    count
            );

            this.getTable().unlockRow(lock);
        } catch ( SerializationException e ) {
            throw new DAOException( e.getMessage(), e );
        } catch ( IOException e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    @Override
    public void reset( Class<? extends IEntity> entity ) throws DAOException {
        this.updateCount( entity, 0L );
    }

    @Override
    public Long count( Class<? extends IEntity> entity ) throws DAOException {
        try {
            Get g = new Get( this.getSerializer().serialize( getEntityName(entity) ) );

            Result result = this.getTable().get(g);
            if ( result.isEmpty() ) {
                return 0L;
            }

            return this.getSerializer().deserealize(
                result.getValue(
                    this.getSerializer().serialize("navirara"),
                    this.getSerializer().serialize("count")
                ),
                Long.class
            );
        } catch ( Throwable e ) {
            throw new DAOException( e.getMessage(), e );
        }
    }
    
    public static String getEntityName( Class<? extends IEntity> clazz ) {
        return clazz.getSimpleName();
    }

}
