package com.redshape.servlet.resources;

import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 17.06.11
 * Time: 14:02
 * To change this template use File | Settings | File Templates.
 */
public interface IWebResourcesHandler extends Serializable {

	public void clear();

    public void addScript( String type, String href );

    public void addStylesheet( String type, String href );

    public void addStylesheet( String type, String href, String media );

    public void addLink( String rel, String type, String href );

    public String printScripts();

    public String printStyles();

}
