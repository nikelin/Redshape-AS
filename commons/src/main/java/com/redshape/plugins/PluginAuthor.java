package com.redshape.plugins;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 30, 2010
 * Time: 4:32:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class PluginAuthor {
    private String name;
    private String email;
    private String url;
    
    public PluginAuthor() {
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
    
}
