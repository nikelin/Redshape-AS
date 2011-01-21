/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.persistence.dao.jpa;

import com.redshape.persistence.dao.DAOException;
import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.dao.query.IQuery;
import com.redshape.persistence.entities.IEntity;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *
 * @author user
 */
public interface IJPADAO<T extends IEntity> extends IDAO<T> {

    public Collection<T> executeQuery( IQuery query ) throws DAOException;

    public List<T> executeQuery( IQuery query, Map<String, Object> parameters ) throws DAOException;

    public List<T> executeQuery( IQuery query, Map<String, Object> parameters, int offset, int limit ) throws DAOException;

}
