package com.redshape.utils.clonners;

public interface IObjectsCloner {

	public <T> T clone( T orig ) throws CloneNotSupportedException;
	
}
