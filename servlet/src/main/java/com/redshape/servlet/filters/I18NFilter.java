/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
