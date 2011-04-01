package com.redshape.applications.bootstrap;

import java.util.Collection;

import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 22, 2010
 * Time: 2:17:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public interface IBootstrapAction {

    public void process() throws BootstrapException;

    public Collection<Object> getDependencies();

    public void addDependency( Object id );

    public boolean hasDependencies();

    public Object getId();

    public void setId( Object id );

    public boolean isProcessed();

    public void markProcessed();

    public boolean isCritical();

    public boolean isError();

    public void markError();

    public void setBootstrap( IBootstrap bootstrap );

    public IBootstrap getBootstrap();
}
