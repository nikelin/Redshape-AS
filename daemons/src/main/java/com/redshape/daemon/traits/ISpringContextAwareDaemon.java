/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
