package com.redshape.plugins.info;

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
public class PluginInfo implements IPluginInfo {
    private String systemPath;
    private String mainClass;
    private String systemId;
    private String version;
    private String description;
    private String name;
    private String url;
    private List<IPluginAuthor> authors;
    private List<PluginUpdateMethod> updateMethods;
    private PluginLoader loader = new DirectoryPluginLoader();

    @Override
	public String getSystemPath() {
        return this.systemPath;
    }

    @Override
	public void setSystemPath(String path) {
        this.systemPath = path;
    }

    @Override
	public PluginLoader getLoader() {
        return this.loader;
    }

    @Override
	public void setLoader(PluginLoader loader) {
        this.loader = loader;
    }

    @Override
	public void setSystemId(String id) {
        this.systemId = id;
    }

    @Override
	public String getSystemId() {
        return this.systemId;
    }

    @Override
	public String getVersion() {
        return this.version;
    }

    @Override
	public void setVerison(String version) {
        this.version = version;
    }

    @Override
	public void setName(String name) {
        this.name = name;
    }

    @Override
	public String getName() {
        return this.name;
    }

    @Override
	public void addAuthor(IPluginAuthor author) {
        this.authors.add( author );
    }

    @Override
	public void addUpdateMethod(PluginUpdateMethod method) {
        this.updateMethods.add( method );
    }

    @Override
	public List<IPluginAuthor> getAuthors() {
        return this.authors;
    }

    @Override
	public List<PluginUpdateMethod> getUpdateMethods() {
        return this.updateMethods;
    }

    @Override
	public void setMainClass(String mainClass) {
        this.mainClass = mainClass;
    }

    @Override
	public String getMainClass() {
        return this.mainClass;
    }

    @Override
	public void setDescription(String description) {
        this.description = description;
    }

    @Override
	public String getDescription() {
        return this.description;
    }

    @Override
	public void setUrl(String url) {
        this.url = url;
    }

    @Override
	public String getUrl() {
        return this.url;
    }
}
