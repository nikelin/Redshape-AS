package com.redshape.persistence.utils;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
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
public final class EntityManagerUtils {
	private static final Logger log = Logger.getLogger( EntityManagerUtils.class );

	public synchronized static void openEntityManager( ApplicationContext appContext ) {
		EntityManagerFactory emf = lookupEntityManagerFactory( appContext );
		boolean participate = false;

		if ( TransactionSynchronizationManager.hasResource(emf)) {
			// Do not modify the EntityManager: just set the participate flag.
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
					closeEntityManager(appContext);
				}
			}
		}
	}

	public synchronized static void closeEntityManager( ApplicationContext context ) {
		TransactionSynchronizationManager.unbindResource( lookupEntityManagerFactory(context) );
				log.debug("Closing JPA EntityManager in OpenEntityManagerInViewFilter");
	}

	/**
	 * Look up the EntityManagerFactory that this filter should use.
	 * The default implementation looks for a bean with the specified name
	 * in Spring's root application context.
	 * @return the EntityManagerFactory to use
	 */
	private static EntityManagerFactory lookupEntityManagerFactory( ApplicationContext appContext ) {
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
	private static EntityManager createEntityManager(EntityManagerFactory emf) {
		return emf.createEntityManager();
	}

}
