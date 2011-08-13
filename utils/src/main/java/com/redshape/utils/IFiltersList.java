package com.redshape.utils;

public interface IFiltersList<T> extends IFilter<T> {

	public void addFilter( IFilter<T> filter );
	
	public void removeFilter( IFilter<T> filter );
	
}
