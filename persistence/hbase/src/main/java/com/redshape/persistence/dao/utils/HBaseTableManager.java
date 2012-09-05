package com.redshape.persistence.dao.utils;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.entities.IEntity;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * User: cwiz
 * Date: 02.11.10
 * Time: 17:14
 */
public class HBaseTableManager implements IHBaseTableManager {
    private static final Logger log = Logger.getLogger(HBaseTableManager.class);
    private static final InterfacesFilter<IEntity> entitiesFilter = new InterfacesFilter<IEntity>(
                                                            new Class[] { IEntity.class } );

    private HBaseAdmin admin;
    private Configuration configuration;
    private IPackagesLoader packagesLoader;
    private String packagesPath;
    private String descriptorFamily;

    public HBaseTableManager(Configuration configuration, HBaseAdmin admin) {
        this.configuration = configuration;
        this.admin = admin;
    }

    protected HBaseAdmin getAdmin() {
        return admin;
    }

    protected Configuration getConfiguration() {
        return configuration;
    }

    public String getPackagesPath() {
        return packagesPath;
    }

    public void setPackagesPath(String packagesPath) {
        this.packagesPath = packagesPath;
    }

    public String getDescriptorFamily() {
        return descriptorFamily;
    }

    public void setDescriptorFamily(String descriptorFamily) {
        this.descriptorFamily = descriptorFamily;
    }

    public IPackagesLoader getPackagesLoader() {
        return packagesLoader;
    }

    public void setPackagesLoader(IPackagesLoader packagesLoader) {
        this.packagesLoader = packagesLoader;
    }

    @Override
    public HTable forEntity(Class<? extends IEntity> entityClazz) throws DAOException {
        return this.forName( this.getNameForEntity(entityClazz) );
    }

    @Override
    public void setup() throws DAOException {
        try {
            for ( Class<? extends IEntity> entityClazz : getPersistenceClasses() ) {
                try {
                    this.createTable(this.getNameForEntity(entityClazz));
                } catch (IOException e) {
                    log.error( e.getMessage(), e );
                }
            }
        } catch (IOException e) {
            throw new DAOException( e.getMessage(), e );
        } catch (PackageLoaderException e) {
            throw new DAOException( e.getMessage(), e );
        }
    }

    @Override
    public void disable(HTable table) throws DAOException {
        try {
            this.getAdmin().disableTable( table.getTableName() );
        } catch ( IOException e ) {
            throw new DAOException("I/O related exception",e );
        }
    }

    @Override
    public void delete(HTable table) throws DAOException {
        try {
            this.getAdmin().deleteTable( table.getTableName() );
        } catch ( IOException e ) {
            throw new DAOException("I/O related exception", e );
        }
    }

    @Override
    public HTable forName(String name) throws DAOException {
        try {
            return new HTable( this.getConfiguration(), name );
        } catch ( IOException e ) {
            throw new DAOException("Unable to establish connection with table", e );
        }
    }

    protected void createTable(String tableName) throws IOException {
        if (admin.tableExists(tableName)) {
            return;
        }

        HTableDescriptor tableDescriptor = new HTableDescriptor(tableName);
        HColumnDescriptor columnDescriptor = new HColumnDescriptor( this.descriptorFamily );
        tableDescriptor.addFamily(columnDescriptor);
        admin.createTable(tableDescriptor);
    }

    protected Class<? extends IEntity>[] getPersistenceClasses() throws IOException, PackageLoaderException {
        return this.getPackagesLoader()
                   .getClasses(packagesPath, entitiesFilter);
    }
    
    protected String getNameForEntity( Class<? extends IEntity> entityClazz ) {
        return entityClazz.getSimpleName();
    }
}
