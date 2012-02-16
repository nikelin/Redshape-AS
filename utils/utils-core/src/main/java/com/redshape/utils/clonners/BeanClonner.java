package com.redshape.utils.clonners;

import org.apache.commons.beanutils.BeanUtils;


public class BeanClonner implements IObjectsCloner {

	@SuppressWarnings("unchecked")
	public <T> T clone ( T orig ) throws CloneNotSupportedException {
		try {
			return (T) BeanUtils.cloneBean(orig);
		} catch ( Throwable e ) {
			throw new CloneNotSupportedException();
		}
	}
	
}
