package com.redshape.servlet.resources;

import com.redshape.servlet.resources.types.Resource;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public interface IWebResourcesCompressor {

    public boolean isSupported( Resource resource );

    public String compress( List<String> paths ) throws IOException;

    public String compress( String path ) throws IOException;

}
