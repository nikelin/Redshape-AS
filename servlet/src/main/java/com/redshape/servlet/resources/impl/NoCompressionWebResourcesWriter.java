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
import com.redshape.servlet.resources.IWebResourcesCompressor;
import com.redshape.servlet.resources.types.Link;
import com.redshape.servlet.resources.types.Script;
import com.redshape.servlet.resources.types.Style;
import com.redshape.servlet.views.ViewHelper;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.yahoo.platform.yui.compressor.YUICompressor;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:14
 * To change this template use File | Settings | File Templates.
 */
public class NoCompressionWebResourcesWriter implements IWebResourceWriter {

    @Override
    public String writeScripts(List<Script> scripts) {
        StringBuilder builder = new StringBuilder();
        for ( Script script : scripts ) {
            builder.append(
                    String.format("<script type=\"%s\" src=\"%s\"></script>\n",
                            script.getType(), ViewHelper.url( script.getHref() ) ) );
        }

        return builder.toString();
    }

    @Override
    public String writeStyles(List<Style> styles) {
        StringBuilder builder = new StringBuilder();
        for ( Style style : styles) {
            builder.append(
                    String.format("<link rel=\"stylesheet\" type=\"%s\" href=\"%s\"/>",
                            style.getType(), ViewHelper.url(style.getHref()) ) );
        }

        return builder.toString();
    }

    @Override
    public String writeLinks(List<Link> links) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
