package com.redshape.render;

import com.redshape.exceptions.ErrorCode;
import com.redshape.exceptions.ExceptionWithCode;

public class RendererException extends ExceptionWithCode {

    public RendererException() {}

	public RendererException( ErrorCode code ) {
		super(code);
	}

}
