package com.redshape.daemon.traits;

import org.springframework.context.ApplicationContext;

import com.redshape.utils.config.ConfigException;
import com.redshape.daemon.DaemonException;
import com.redshape.daemon.IDaemonAttributes;

public interface ISpringContextAwareDaemon<T extends IDaemonAttributes> 
					extends IDaemon<T> {
	
	public ApplicationContext getContext();
	
	public void setContext(ApplicationContext context) throws ConfigException, DaemonException;
	
	public void setContext(ApplicationContext context, boolean reloadConfiguration)
			throws ConfigException, DaemonException;
	
}
