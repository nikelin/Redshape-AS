/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.persistence.jms;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.jms.IRequestHandlingService;
import com.redshape.persistence.jms.core.IDao;
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

    protected IDao getDAO() {
        return this.getContext().getBean(IDao.class);
    }

    protected IRequestHandlingService getService() {
        return this.getContext().getBean(IRequestHandlingService.class);
    }

    public void testMain() throws DAOException {
//        new Thread( this.getService() ).start();
//
//        int attempts = 0;
//        while ( attempts++ < 100 ) {
//            this.getDAO().findAll().list();
//
//            try {
//                Thread.sleep(1000);
//            } catch ( InterruptedException e ) {  }
//        }
    }

}
