package com.redshape.plugins;

import com.redshape.plugins.loaders.DirectoryPluginLoader;
import com.redshape.plugins.loaders.PluginLoader;
import com.redshape.plugins.update.PluginUpdateMethod;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:27:36 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginInfo {
    private String systemPath;
    private String mainClass;
    private String systemId;
    private String version;
    private String description;
    private String name;
    private String url;
    private List<PluginAuthor> authors;
    private List<PluginUpdateMethod> updateMethods;
    private PluginLoader loader = new DirectoryPluginLoader();

    public String getSystemPath() {
        return this.systemPath;
    }

    public void setSystemPath( String path ) {
        this.systemPath = path;
    }

    public PluginLoader getLoader() {
        return this.loader;
    }

    public void setLoader( PluginLoader loader ) {
        this.loader = loader;
    }

    public void setSystemId( String id ) {
        this.systemId = id;
    }

    public String getSystemId() {
        return this.systemId;
    }

    public String getVersion() {
        return this.version;
    }

    public void setVerison( String version ) {
        this.version = version;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addAuthor( PluginAuthor author ) {
        this.authors.add( author );
    }

    public void addUpdateMethod( PluginUpdateMethod method ) {
        this.updateMethods.add( method );
    }

    public List<PluginAuthor> getAuthors() {
        return this.authors;
    }

    public List<PluginUpdateMethod> getUpdateMethods() {
        return this.updateMethods;
    }

    public void setMainClass( String mainClass ) {
        this.mainClass = mainClass;
    }

    public String getMainClass() {
        return this.mainClass;
    }

    public void setDescription( String description ) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void setUrl( String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }
}
