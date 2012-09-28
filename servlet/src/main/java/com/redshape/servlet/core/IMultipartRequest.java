package com.redshape.servlet.core;

import java.util.Iterator;

/**
 * @package com.redshape.servlet.core
 * @user cyril
 * @date 7/27/11 4:29 PM
 */
public interface IMultipartRequest {

    interface IFileInfo {

        public byte [] getContent();

        public String getSourceFilename();

        public String getContentType();

        public void setSourceFileName(String fileName);

    }

    public Iterator getParameterNames();

    public String getParameter(String name);

    public String [] getParameterValues(String name);

    public Iterator getFileInfoNames();

    public MultipartRequest.IFileInfo getFileInfo(String name);

    public MultipartRequest.IFileInfo[] getFileInfoValues(String name);
}
