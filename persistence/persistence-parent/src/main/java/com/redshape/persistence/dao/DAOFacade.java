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

package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Jellical
 * Date: 16.04.11
 * Time: 18:20
 * To change this template use File | Settings | File Templates.
 */
public class DAOFacade implements IDAOFacade, ApplicationContextAware {
	private ApplicationContext context;

    private Map<Class<? extends IEntity>, IDAO<?>> cached
            = new HashMap<Class<? extends IEntity>, IDAO<?>>();

    @Override
	public void setApplicationContext( ApplicationContext context ) {
		this.context = context;
	}

	protected ApplicationContext getApplicationContext() {
		return this.context;
	}

    @Override
	public <V extends IEntity, T extends IDAO<V>> T getDAO( Class<? extends V> clazz ) {
        T result = (T) this.cached.get(clazz);
        if ( result != null ) {
            return result;
        }

		for ( IDAO<?> daoBean : this.getApplicationContext().getBeansOfType(IDAO.class).values() ) {
            Class<?> entityClazz = daoBean.getEntityClass();
            if ( entityClazz.equals(clazz) ) {
                result = (T)daoBean;
                break;
            } else if ( entityClazz.isAssignableFrom(clazz) ) {
                result = (T) daoBean;
            }
        }

        if ( result != null ) {
            this.cached.put( clazz, result );
        }

        return result;
	}

}
