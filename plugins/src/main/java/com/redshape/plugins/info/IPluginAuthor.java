package com.redshape.plugins.info;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.plugins.info
 * @date 9/19/11 8:11 PM
 */
public interface IPluginAuthor {
	String getName();

	void setName(String name);

	String getEmail();

	void setEmail(String email);

	String getUrl();

	void setUrl(String url);
}
