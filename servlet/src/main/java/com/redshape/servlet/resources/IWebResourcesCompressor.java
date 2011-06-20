package com.redshape.servlet.resources;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:22
 * To change this template use File | Settings | File Templates.
 */
public interface IWebResourcesCompressor {

    public String compress( String data ) throws IOException;

}
