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

package com.redshape.servlet.views;

import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.render.IViewRenderer;
import com.redshape.servlet.views.render.RenderException;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:32 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IView extends Serializable {

    public void setException( ProcessingException e );

    public ProcessingException getException();

    public String getError();

    public void setError( String message );

	public void setRedirection( String path );
	
	public String getRedirection();
	
    public String getExtension();

    public void setExtension( String value );

    public void setAttribute( ViewAttributes name, Object value );
    
    public void setAttribute( String name, Object value );

    public <T> T getAttribute( ViewAttributes name );
    
    public <T> T getAttribute( String name );

    public Map<String, Object> getAttributes();

    public void setBasePath( String path );

    public String getBasePath();

    public String getScriptPath();

    public void setViewPath( String path );

    public String getViewPath();

    public void render() throws RenderException;

    public void setRenderer( IViewRenderer engine );

    public IViewRenderer getRenderer();

    public ILayout getLayout();

    public void setLayout( ILayout layout );

    public void reset( ResetMode mode );

}
