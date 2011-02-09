package com.redshape.renderer;

public class RendererException extends Throwable {

    public RendererException() {
    	super();
    }

	public RendererException( String code ) {
		super(code);
	}
	
	public RendererException( String code, Throwable e ) {
		super(code, e);
	}

}
