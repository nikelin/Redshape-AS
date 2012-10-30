package com.redshape.applications.bootstrap;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:17:17 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBootstrapAction {

    public void process() throws BootstrapException;

    public Collection<Object> getDependencies();

    public void addDependency( Object id );

    public boolean hasDependencies();

    public Object getId();

	public boolean hasMarkers( ActionMarker... markers );

	public void addMarker( ActionMarker marker );

	public boolean hasMarker( ActionMarker marker );

	public Collection<ActionMarker> getMarkers();

    public void setBootstrap( IBootstrap bootstrap );

    public IBootstrap getBootstrap();
}
