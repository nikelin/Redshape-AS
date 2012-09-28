package com.redshape.servlet.actions.exceptions.handling;

import com.redshape.servlet.actions.exceptions.PageAccessDeniedException;
import com.redshape.servlet.actions.exceptions.PageAuthRequiredException;
import com.redshape.servlet.actions.exceptions.PageNotFoundException;
import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.controllers.ProcessingException;

import java.io.IOException;

/**
 * Interface for processing related exceptions raised in a
 * controllers context.
 *
 * @package com.redshape.servlet.actions.exceptions
 * @user cyril
 * @date 6/20/11 10:43 PM
 */
public interface IPageExceptionHandler {

    public void handleException( PageNotFoundException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException;

    public void handleException( PageAuthRequiredException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

    public void handleException( PageAccessDeniedException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

    public void handleException( ProcessingException e, IHttpRequest request,
                                 IHttpResponse response )
		throws IOException ;

	public String getPage500();

	public String getPage404();

	public String getPage403();

	public String getPage401();


}
