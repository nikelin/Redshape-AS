package com.redshape.persistence;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.IDAO;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 5:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ITestDAO extends IDAO<TestEntity> {

    public List<TestEntity> findByIds( Long[] ids ) throws DAOException;

}
