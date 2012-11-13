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

package com.redshape.persistence;

import com.redshape.persistence.core.EntityRecord;
import com.redshape.persistence.core.IEntityDAO;
import com.redshape.persistence.dao.DAOException;
import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 4:33 PM
 * To change this template use File | Settings | File Templates.
 */
public class DAOTest extends AbstractContextAwareTest<String> {

    public DAOTest() {
        super("src/test/resources/context.xml");
    }
    
    public IEntityDAO getTestDAO() {
        return this.getContext().getBean(IEntityDAO.class);
    }

    @Test
    public void testMain() throws DAOException {
        IEntityDAO dao = this.getTestDAO();
        dao.removeAll();
        Assert.assertEquals((Long) dao.count(), (Long) 0L);
        EntityRecord entity = new EntityRecord();
        entity.setName("Afla!");
        entity = dao.save( entity );
        Assert.assertNotNull(entity.getId());

        List<EntityRecord> results = dao.findAll().list();
        Assert.assertEquals( (Long) dao.count(), Long.valueOf(results.size()) );
        Assert.assertEquals( results.get(0).getName(), entity.getName() );
    }
}
