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

import com.redshape.servlet.core.controllers.IAction;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 1:56 AM
 * To change this template use File | Settings | File Templates.
 */
public class Layout extends AbstractView implements ILayout {
    private IView content;
    
    private IAction dispatchAction;

    public Layout( String basePath, String viewPath, String extension ) {
        this(null, basePath, viewPath, extension);
    }

    public Layout( IAction dispatchAction, String basePath, String viewPath, String extension ) {
        super(basePath, viewPath, extension);
        
        this.dispatchAction = dispatchAction;
    }

    @Override
    public IAction getDispatchAction() {
        return this.dispatchAction;
    }

    public void setContent( IView content ) {
        this.content = content;
    }

    public IView getContent() {
        return this.content;
    }

    @Override
    public void setLayout( ILayout layout ) {
    }

    @Override
    public ILayout getLayout() {
        return  null;
    }

}
