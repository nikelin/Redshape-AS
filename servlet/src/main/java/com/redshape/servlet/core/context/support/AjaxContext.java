package com.redshape.servlet.core.context.support;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.IView;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author nikelin
 * @date 14:01
 */
public class AjaxContext implements IResponseContext {
    private static final Logger log = Logger.getLogger( AjaxContext.class );
    private static final String MARKER_HEADER = "XMLHttpRequest";

    @Override
    public SupportType isSupported(IHttpRequest request) {
        String headerValue = request.getHeader("X-Requested-With");
         if (  headerValue != null && headerValue.equals(AjaxContext.MARKER_HEADER) ) {
            return SupportType.SHOULD;
        } else {
            return SupportType.NO;
        }
    }

    @Override
    public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response) throws ProcessingException {
        try {
            this.writeJsonResponse( view.getAttributes(), response );
        } catch ( IOException e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }

    private void writeResponse(String responseData, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        response.getWriter().write(responseData);
    }

    protected void writeJsonResponse( Object value, HttpServletResponse response ) throws IOException {
        this.writeResponse( JSONObject.fromObject( value ).toString(), response );
    }

    protected void writeJsonResponse(String rootName, Object value, HttpServletResponse response) throws IOException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(rootName, value);
        jsonObject.put("success", true);

        this.writeResponse(jsonObject.toString(), response);
    }
}
