package com.redshape.persistence;

import com.redshape.persistence.dao.DAOException;
import com.redshape.utils.tests.AbstractContextAwareTest;
import org.junit.Assert;

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
    
    public ITestDAO getTestDAO() {
        return this.getContext().getBean(ITestDAO.class);
    }

    public void testMain() throws DAOException {
        ITestDAO dao = this.getTestDAO();
        dao.removeAll();
        Assert.assertEquals((Long) dao.count(), (Long) 0L);
        TestEntity entity = new TestEntity();
        entity.setName("Afla!");
        entity = dao.save( entity );
        Assert.assertNotNull(entity.getId());

        List<TestEntity> results = dao.findAll().list();
        Assert.assertEquals( (Long) dao.count(), Long.valueOf(results.size()) );
        Assert.assertEquals( results.get(0).getName(), entity.getName() );
    }
}
