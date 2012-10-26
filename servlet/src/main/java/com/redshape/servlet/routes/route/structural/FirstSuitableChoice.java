package com.redshape.servlet.routes.route.structural;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.routes.IRoute;

import java.util.Set;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.routes.route.structural
 * @date 10/24/11 8:14 PM
 */
public class FirstSuitableChoice implements IRoute {
	private Set<IRoute> routes;

	public FirstSuitableChoice( Set<IRoute> routes ) {
		if ( routes == null ) {
			throw new IllegalArgumentException("<null>");
		}

		this.routes = routes;
	}

	@Override
	public boolean isApplicable(IHttpRequest request) {
		for ( IRoute route : routes ) {
			if ( route.isApplicable(request) ) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void apply(IHttpRequest request) {
		for ( IRoute route : routes ) {
			if ( route.isApplicable(request) ) {
				route.apply(request);
				return;
			}
		}
	}
}
