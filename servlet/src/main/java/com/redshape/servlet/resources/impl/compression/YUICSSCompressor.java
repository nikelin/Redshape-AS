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

package com.redshape.servlet.resources.impl.compression;

import com.redshape.servlet.resources.IWebResourcesCompressor;
import com.redshape.servlet.resources.types.Resource;
import com.redshape.utils.Commons;
import com.redshape.utils.IResourcesLoader;
import com.yahoo.platform.yui.compressor.CssCompressor;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/29/12
 * Time: 5:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class YUICSSCompressor implements IWebResourcesCompressor {

    private int lineBreakOps;
    private String resourcesRoot;
    private IResourcesLoader loader;


    public YUICSSCompressor( IResourcesLoader loader, String resourcesRoot ) {
        Commons.checkNotNull(loader);
        Commons.checkNotNull(resourcesRoot);

        this.loader = loader;
        this.resourcesRoot = resourcesRoot;
    }

    public void setLineBreakOps(int lineBreakOps) {
        this.lineBreakOps = lineBreakOps;
    }

    public int getLineBreakOps() {
        return lineBreakOps;
    }

    protected IResourcesLoader getLoader() {
        return loader;
    }


    protected String getResourcesRoot() {
        return this.resourcesRoot;
    }

    protected InputStreamReader load( String path ) throws IOException {
        return new InputStreamReader( this.getLoader().loadResource( this.getResourcesRoot() + File.separator + path) );
    }

    @Override
    public boolean isSupported(Resource resource) {
        return resource.getHref().endsWith("css")
                && !resource.getHref().contains("://");
    }

    @Override
    public String compress(List<String> paths) throws IOException {
        StringBuilder builder = new StringBuilder();
        for ( String path : paths ) {
            builder.append( this.compress(path) );
        }

        return builder.toString();
    }

    @Override
    public String compress(String path) throws IOException {
        CssCompressor compressor = new CssCompressor( this.load(path) );

        StringWriter writer = new StringWriter();
        compressor.compress( writer, this.getLineBreakOps() );
        return writer.toString();
    }
}
