package com.vio.plugins.sources;

import com.vio.plugins.info.InfoFile;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 5:44:20 PM
 * To change this template use File | Settings | File Templates.
 */
public interface PluginSource {

    public String getPath();

    public InfoFile getInfoFile() throws IOException;

}
