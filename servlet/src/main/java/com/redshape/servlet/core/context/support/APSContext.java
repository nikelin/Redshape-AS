package com.redshape.servlet.core.context.support;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;

import java.io.File;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.core.context.support
 * @date 8/13/11 12:36 AM
 */
public class APSContext implements IResponseContext {
	public static final String EXTENSION = "aps";

	@Override
	public SupportType isSupported(IHttpRequest request) {
		return SupportType.MAY;
	}

	@Override
	public SupportType isSupported(IView view) {
		if ( !view.getException().equals( EXTENSION ) ) {
			return SupportType.NO;
		}

		return SupportType.MAY;
	}

	@Override
	public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response) throws ProcessingException {
		String path = view.getLayout().getBasePath() + File.separator + view.getScriptPath();

	}
}
