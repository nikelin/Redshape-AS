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

package com.redshape.servlet.resources.impl;

import com.redshape.servlet.resources.IWebResourceWriter;
import com.redshape.servlet.resources.IWebResourcesHandler;
import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;
import com.redshape.servlet.views.ViewHelper;
import com.redshape.utils.Commons;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:03
 * To change this template use File | Settings | File Templates.
 */
public class WebResourcesHandler implements IWebResourcesHandler {
    private IWebResourceWriter writer;

    private List<Script> scripts = new ArrayList<Script>();
    private List<Style> styles = new ArrayList<Style>();
    private List<Link> links = new ArrayList<Link>();

    public WebResourcesHandler(IWebResourceWriter writer) {
        Commons.checkNotNull(writer);

        this.writer = writer;
    }

    protected IWebResourceWriter getWriter() {
        return this.writer;
    }

    @Override
	public void clear() {
		this.scripts.clear();
		this.styles.clear();
	}

	@Override
    public void addScript(String type, String href) {
        Script script = new Script( type, href );
        if ( this.scripts.contains(script) ) {
            return;
        }

        this.scripts.add( script );
    }

    @Override
    public void addLink(String rel, String type, String href) {
        Link link = new Link( rel, type, href );
        if ( this.links.contains(link) ) {
            return;
        }

        this.links.add( link );
    }

    @Override
    public void addStylesheet( String type, String href ) {
        this.addStylesheet( type, href, Style.DEFAULT_MEDIA );
    }

    @Override
    public void addStylesheet(String type, String href, String media ) {
        Style style = new Style( type, href, media );
        if ( this.styles.contains(style) ) {
            return;
        }

        this.styles.add( style );
    }

    @Override
    public String printScripts() {
        return this.getWriter().writeScripts( this.scripts );
    }

    @Override
    public String printStyles() {
        return this.getWriter().writeStyles( this.styles );
    }

}
