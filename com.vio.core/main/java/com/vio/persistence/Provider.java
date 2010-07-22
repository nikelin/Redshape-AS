package com.vio.persistence;

import com.vio.config.readers.ConfigReaderException;
import com.vio.persistence.managers.IManagersFactory;
import com.vio.persistence.managers.ManagersFactory;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLException;

public final class Provider {
    private static final Logger log = Logger.getLogger( Provider.class );
    private static IManagersFactory managersFactory = ManagersFactory.getDefault();
    private static EntityManagerFactory ejbFactory;
    private static EntityManager ejbManager;

    public static EntityManagerFactory buildManagersFactory() throws ConfigReaderException, SQLException, ProviderException {
        return managersFactory.getEJBFactory();
    }

    public static EntityManager createManager() throws ConfigReaderException, SQLException, ProviderException {
        return getEJBFactory().createEntityManager();
    }

    public static EntityManager getManager() throws ConfigReaderException, SQLException, ProviderException {
        if ( ejbManager == null || !ejbManager.isOpen() ) {
            ejbManager = createManager();
        }

        return ejbManager;
    }

    public static void setManagersFactory( IManagersFactory factory ) {
        managersFactory = factory;
    }

    public static IManagersFactory getManagersFactory() {
        return managersFactory;
    }

    public static EntityManagerFactory getEJBFactory() throws ConfigReaderException, SQLException, ProviderException {
        if (null == ejbFactory) {
            ejbFactory = buildManagersFactory();
        }

        return ejbFactory;
    }

}
