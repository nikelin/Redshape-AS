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

package com.redshape.servlet.core.controllers.loaders;

import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.utils.IPackagesLoader;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.core.controllers.loaders
 * @date 8/13/11 12:31 PM
 */
public class StandardPackageActionsLoader implements IActionsLoader {
	private static final Logger log = Logger.getLogger( StandardPackageActionsLoader.class );
	private Set<String> destinationPackages;

	private IPackagesLoader loader;

	public StandardPackageActionsLoader(Set<String> destinationPackages, IPackagesLoader loader ) {
		if ( destinationPackages == null || loader == null ) {
			throw new IllegalArgumentException("<null>");
		}

		this.destinationPackages = destinationPackages;
		this.loader = loader;
	}

	protected IPackagesLoader getLoader() {
		return loader;
	}

	@Override
	public Set<Class<? extends IAction>> load() {
		Set<Class<? extends IAction>> result = new HashSet<Class<? extends IAction>>();
		for ( String destination : this.destinationPackages ) {
			try {
				result.addAll(
					Arrays.<Class<? extends IAction>>asList(
						this.getLoader()
							.<IAction>getClasses(
								destination,
								new InterfacesFilter(
										new Class[]{ IAction.class },
										new Class[]{ Action.class }
								)
					) )
				);
			} catch ( PackageLoaderException e ) {
				log.error( "Unable to load actions package!", e );
			}
		}

		return result;
	}
}
