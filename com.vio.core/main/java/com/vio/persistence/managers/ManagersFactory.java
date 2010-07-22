package com.vio.persistence.managers;

import com.vio.config.readers.ConfigReaderException;
import com.vio.persistence.ProviderException;
import com.vio.persistence.entities.Entity;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:44:55 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ManagersFactory implements IManagersFactory {
    private static Logger log = Logger.getLogger( ManagersFactory.class );
    private static IManagersFactory defaultInstance = new HibernateManagersFactory();
    private static Map<Class<? extends IManagersFactory>, IManagersFactory> factories = new HashMap<Class<? extends IManagersFactory>, IManagersFactory>();
    private Map<Class<? extends Manager>, Manager> managers = new HashMap<Class<? extends Manager>, Manager>();
    private Map<Class<? extends Entity>, Manager> entities = new HashMap<Class<? extends Entity>, Manager>();

    public static IManagersFactory getDefault() {
        return defaultInstance;
    }

    public ManagersFactory() {
        try {
            this.initializeManagers();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );       
        }
    }

    public static IManagersFactory createInstance( Class<? extends IManagersFactory> clazz ) throws InstantiationException, IllegalAccessException {
        IManagersFactory instance = factories.get(clazz);
        if ( instance != null ) {
            return instance;
        }

        instance = clazz.newInstance();

        factories.put( clazz, instance );

        return instance;
    }

    public Manager getForEntity( Entity entity ) throws ManagerException {
        return this.getForEntity( entity.getClass() );
    }

    public Manager getForEntity( Class entity ) throws ManagerException {
        Manager v = this.entities.get(entity);
        if ( v != null ) {
            return v;
        }

        try {
            v = this.forEntity( entity );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ManagerException();
        }

        this.entities.put( entity, v );

        return v;
    }

    protected Collection<Manager> getManagers() {
        return this.managers.values();
    }

    protected Manager getDefaultManagerFor( Class<? extends Entity> clazz ) {
        return new Manager( clazz );
    }

    abstract protected Manager forEntity( Class<? extends Entity> entity ) throws InstantiationException;

    public <T extends Manager> T getManager( Class<T> clazz ) {
        try {
            Manager v =this.managers.get(clazz);
            if ( v != null ) {
                return (T) v;
            }

            v = clazz.newInstance();

            this.managers.put( clazz, v );

            return (T) v;
        } catch ( Throwable e ) {
            return null;
        }
    }

    private void initializeManagers() throws PackageLoaderException {
        for( Class managerClazz : Registry.getPackagesLoader().getClasses("com.vio.persistence.managers", new InterfacesFilter( new Class[] { Manager.class} ) ) ) {
            try {
                Manager manager = (Manager) managerClazz.newInstance();
                this.managers.put( managerClazz, (Manager) managerClazz.newInstance() );
                this.entities.put( manager.getEntityClass(), manager );
            } catch ( Throwable e ) {
            }
        }
    }
}
