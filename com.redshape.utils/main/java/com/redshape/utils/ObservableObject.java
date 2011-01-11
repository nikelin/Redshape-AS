package com.redshape.utils;

import java.util.Observer;

public interface ObservableObject {
	
	public void notifyObservers( Object arg );
	
	public void addObserver( Observer object );
}
