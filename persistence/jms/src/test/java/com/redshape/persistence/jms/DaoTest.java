package com.redshape.persistence.jms;

import com.redshape.persistence.dao.DAOException;
import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Test;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {4:31 PM}
 */
public class DaoTest extends AbstractContextAwareTest {

    public DaoTest() {
        super("src/test/resources/context.xml");
    }

    protected ITestDao getDAO() {
        return this.getContext().getBean(ITestDao.class);
    }

    protected IRequestHandlingService getService() {
        return this.getContext().getBean(IRequestHandlingService.class);
    }

    @Test
    public void testMain() throws DAOException {
        new Thread( this.getService() ).start();

        int attempts = 0;
        while ( attempts++ < 100 ) {
            this.getDAO().findAll().list();

            try {
                Thread.sleep(1000);
            } catch ( InterruptedException e ) {  }
        }
    }

}
