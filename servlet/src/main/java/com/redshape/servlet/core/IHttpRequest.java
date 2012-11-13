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

import com.redshape.form.IUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRequest extends HttpServletRequest, IUserRequest {

    public static final String FAILED_TO_PROCESS = "__requestProcessingFailed";
    public static final String CONTEXT_TYPE_SELECTED = "__contextTypeSelected";

    public boolean isMultiPart();

    public boolean isPost();

    public void setController( String name );

    public String getController();

    public void setAction( String name );

    public String getAction();

    public String getCookie( String name );

	public Long getLongParameter( String name );

	public Integer getIntegerParameter( String name );

	public Boolean getBooleanParameter( String name );

	public Boolean getCheckboxParameter( String name );

    public <T> List<T> getListParameter( String name );

	public Float getFloatParameter( String name );

    public <T> T getObjectParameter( String name ) throws IOException;

	public void setParameter( String name, Object value );

    public void setParameters( Map<String, Object> parameters );

    public boolean hasParameter( String name );

    public String getBody() throws IOException;

    public byte[] getFileContent( String name ) throws IOException;

    public IMultipartRequest getMultipart() throws IOException;

}
