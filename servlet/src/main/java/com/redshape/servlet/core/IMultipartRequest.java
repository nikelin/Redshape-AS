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
