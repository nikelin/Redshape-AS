package com.redshape.persistence.dao;

import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.entities.IEntity;
import org.springframework.context.ApplicationContext;

/**
 * Created by IntelliJ IDEA.
 * User: Jellical
 * Date: 16.04.11
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class DAOFacade implements IDAOFacade {
	private ApplicationContext context;

	public DAOFacade() {}

	public void setApplicationContext( ApplicationContext context ) {
		this.context = context;
	}

	protected ApplicationContext getApplicationContext() {
		return this.context;
	}

	public <V extends IEntity, T extends IDAO<V>> T getDAO( Class<T> clazz ) {
		return this.getApplicationContext().getBean( clazz );
	}

}
