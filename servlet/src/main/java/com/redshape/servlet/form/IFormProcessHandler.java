package com.redshape.servlet.form;

public interface IFormProcessHandler<T extends IForm> {

	public void process( T form );
	
}
