package com.vio.render;

import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;

public class RendererException extends ExceptionWithCode {

    public RendererException() {}

	public RendererException( ErrorCode code ) {
		super(code);
	}

}
