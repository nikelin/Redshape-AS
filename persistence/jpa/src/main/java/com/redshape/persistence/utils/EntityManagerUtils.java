package com.redshape.persistence.utils;

import com.redshape.persistence.entities.IEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.orm.jpa.EntityManagerHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.utils
 * @date 8/20/11 2:48 PM
 */
public final class EntityManagerUtils implements ISessionManager, ApplicationContextAware {
    private static final Logger log = Logger.getLogger( EntityManagerUtils.class );

    private ApplicationContext context;

    public EntityManagerUtils() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    protected ApplicationContext getContext() {
        return this.context;
    }

    public synchronized void open() {
        EntityManagerFactory emf = lookupEntityManagerFactory( this.getContext() );
        boolean participate = false;

        if ( TransactionSynchronizationManager.hasResource(emf)) {
            participate = true;
        }
        else {
            log.debug("Opening JPA EntityManager in OpenEntityManagerInViewFilter");
            try {
                EntityManager em = createEntityManager(emf);
                TransactionSynchronizationManager.bindResource(emf, new EntityManagerHolder(em));
                participate = true;
            }
            catch (PersistenceException ex) {
                throw new DataAccessResourceFailureException("Could not create JPA EntityManager", ex);
            }  finally {
                if ( !participate ) {
                    close();
                }
            }
        }
    }

    @Override
    public IEntity refresh( IEntity object ) {
        EntityManagerFactory emf = lookupEntityManagerFactory(this.getContext());
        if ( !TransactionSynchronizationManager.hasResource(emf) ) {
            throw new IllegalStateException("Session closed");
        }

        EntityManagerHolder holder = (EntityManagerHolder) TransactionSynchronizationManager
                .getResource( emf );
        EntityManager em = holder.getEntityManager();
        
        if ( !em.contains(object) ) {
            object = em.find( object.getClass(), object.getId() );
        }
        
        em.refresh(object);

        return object;
    }
    
    public synchronized void close() {
        TransactionSynchronizationManager.unbindResource( lookupEntityManagerFactory(
                this.getContext()
        ) );
        log.debug("Closing JPA EntityManager in OpenEntityManagerInViewFilter");
    }

    /**
     * Look up the EntityManagerFactory that this filter should use.
     * The default implementation looks for a bean with the specified name
     * in Spring's root application context.
     * @return the EntityManagerFactory to use
     */
    private EntityManagerFactory lookupEntityManagerFactory( ApplicationContext appContext ) {
        if (log.isDebugEnabled()) {
            log.debug("Using EntityManagerFactory for OpenEntityManagerInViewFilter");
        }

        return appContext.getBean(EntityManagerFactory.class);
    }

    /**
     * Create a JPA EntityManager to be bound to a request.
     * <p>Can be overridden in subclasses.
     * @param emf the EntityManagerFactory to use
     * @see javax.persistence.EntityManagerFactory#createEntityManager()
     */
    private EntityManager createEntityManager(EntityManagerFactory emf) {
        return emf.createEntityManager();
    }

}
