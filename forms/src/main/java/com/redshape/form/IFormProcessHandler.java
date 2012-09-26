package com.redshape.form;

public interface IFormProcessHandler<T extends IForm> {

	public boolean process( T form );
	
}
