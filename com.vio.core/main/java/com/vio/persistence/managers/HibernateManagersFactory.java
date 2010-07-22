package com.vio.persistence.managers;

import com.vio.config.IDatabaseConfig;
import com.vio.config.readers.ConfigReaderException;
import com.vio.persistence.ProviderException;
import com.vio.persistence.entities.Entity;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:50:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class HibernateManagersFactory extends ManagersFactory {
    private static final Logger log = Logger.getLogger( HibernateManagersFactory.class );

    public Manager forEntity( Class<? extends Entity> entity ) throws InstantiationException {
        try {
            for ( Manager m : this.getManagers() ) {
                if ( m.getEntityClass().isAssignableFrom( entity.getClass() ) ) {
                    return m;
                }
            }

            return this.getDefaultManagerFor( entity );
        } catch ( Throwable e ) {
            throw new InstantiationException("Method to create validator for given entity is not exists.");
        }
    }

    public Map<String, Object> createConfigObject() throws ConfigReaderException {
        IDatabaseConfig config = Registry.getDatabaseConfig();        

        Map<String, Object> override = new HashMap<String, Object>();
        override.put("hibernate.connection.driver_class", config.getDatabaseAdapter() );
        override.put("hibernate.connection.url", config.getDatabaseUri() );
        override.put("hibernate.connection.username", config.getDatabaseUser() );
        override.put("hibernate.connection.password", config.getDatabasePassword() );
        override.put("hibernate.connection.useUnicode", config.isDatabaseUTF8Connection() ? "true" : "false" );
        override.put("hibernate.connection.characterEncoding", config.getDatabaseCharset() );

        return override;
    }

    public EntityManagerFactory getEJBFactory() throws ConfigReaderException, SQLException, ProviderException {
       return Persistence.createEntityManagerFactory( Registry.getDatabaseConfig().getPersistenceUnit(), this.createConfigObject() );
    }
}
