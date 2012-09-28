package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;

/**
 * Created by IntelliJ IDEA.
 * User: Jellical
 * Date: 16.04.11
 * Time: 18:21
 * To change this template use File | Settings | File Templates.
 */
public interface IDAOFacade {

	public <V extends IEntity, T extends IDAO<V>> T getDAO( Class<? extends V> clazz );

}
