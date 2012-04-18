package com.redshape.servlet.filters;

import org.apache.log4j.Logger;
import org.apache.xmlbeans.impl.common.IOUtil;
import org.xml.sax.SAXException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.TransformerConfigurationException;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/18/12
 * Time: 8:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class I18NFilter extends AbstractJSPFilter {

    @Override
    protected void process( HttpServletRequest request,
                            HttpServletResponse response,
                            InputStream in,
                            OutputStream out ) throws ServletException, IOException {
        String page = this.readString(in);

    }

}
