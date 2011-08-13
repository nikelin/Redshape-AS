package com.redshape.servlet.core;

import java.util.Iterator;

/**
 * @package com.redshape.servlet.core
 * @user cyril
 * @date 7/27/11 4:29 PM
 */
public interface IMultipartRequest {

    public Iterator getParameterNames();

    public String getParameter(String name);

    public String [] getParameterValues(String name);

    public Iterator getFileInfoNames();

    public HttpRequest.MultipartRequest.FileInfo getFileInfo(String name);

    public HttpRequest.MultipartRequest.FileInfo[] getFileInfoValues(String name);
}
