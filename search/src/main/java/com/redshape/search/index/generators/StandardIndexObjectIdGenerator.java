package com.redshape.search.index.generators;

import com.redshape.persistence.entities.IEntity;
import com.redshape.search.index.IIndexObjectIdGenerator;

import java.util.Date;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.index.generators
 * @date 9/5/11 3:28 PM
 */
public class StandardIndexObjectIdGenerator implements IIndexObjectIdGenerator {

	@Override
	public <T> T generate(Object entity) {
		T result;
		if ( entity instanceof IEntity ) {
			result = (T) (( IEntity ) entity).getId();
		} else {
			result = (T) Long.valueOf( new Date().getTime() );
		}

		return result;
	}

}
