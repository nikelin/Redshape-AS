package com.redshape.servlet.core.controllers.loaders;

import com.redshape.servlet.core.controllers.Action;
import com.redshape.servlet.core.controllers.IAction;
import com.redshape.servlet.core.controllers.IActionsLoader;
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
