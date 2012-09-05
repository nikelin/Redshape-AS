package com.redshape.renderer;

public class RendererException extends Throwable {
	private static final long serialVersionUID = -5951570218329123262L;

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
