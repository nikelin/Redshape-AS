package com.redshape.servlet.support.ascript;

import com.redshape.ascript.EvaluationException;
import com.redshape.ascript.IEvaluator;
import com.redshape.ascript.context.IEvaluationContext;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.core.context.support
 * @date 8/13/11 12:36 AM
 */
public class APSContext implements IResponseContext {
	public static final String EXTENSION = "aps";

	@Autowired( required = true )
	private IEvaluator evaluator;

	public IEvaluator getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(IEvaluator evaluator) {
		this.evaluator = evaluator;
	}

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
	public void proceedResponse(IView view, IHttpRequest request,
								IHttpResponse response) throws ProcessingException {
		try {
			IEvaluationContext context = this.getEvaluator().createContext("web");

			response.getWriter().write(
					String.valueOf( this.getEvaluator().evaluateFile( view.getLayout().getBasePath()  ) ) );
		} catch ( IOException e ) {
			throw new ProcessingException("IO related exception", e );
		} catch ( EvaluationException e ) {
			throw new ProcessingException("Script evaluation exception", e );
		}
	}
}
