package com.redshape.plugins.info;

import com.redshape.plugins.loaders.PluginLoader;
import com.redshape.plugins.update.PluginUpdateMethod;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins
 * @date 9/19/11 8:09 PM
 */
public interface IPluginInfo {
	String getSystemPath();

	void setSystemPath(String path);

	PluginLoader getLoader();

	void setLoader(PluginLoader loader);

	void setSystemId(String id);

	String getSystemId();

	String getVersion();

	void setVerison(String version);

	void setName(String name);

	String getName();

	void addAuthor(IPluginAuthor author);

	void addUpdateMethod(PluginUpdateMethod method);

	List<IPluginAuthor> getAuthors();

	List<PluginUpdateMethod> getUpdateMethods();

	void setMainClass(String mainClass);

	String getMainClass();

	void setDescription(String description);

	String getDescription();

	void setUrl(String url);

	String getUrl();
}
