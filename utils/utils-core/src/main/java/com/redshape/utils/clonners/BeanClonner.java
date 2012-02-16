package com.redshape.utils.clonners;

import org.apache.commons.beanutils.BeanUtils;


public class BeanClonner implements IObjectsClonner {

	@SuppressWarnings("unchecked")
	public <T> T clone ( T orig ) {
		try {
			return (T) BeanUtils.cloneBean(orig);
		} catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
	}
	
}
