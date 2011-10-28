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
	public boolean isApplicatable(IHttpRequest request) {
		for ( IRoute route : routes ) {
			if ( route.isApplicatable(request) ) {
				return true;
			}
		}

		return false;
	}

	@Override
	public void applicate(IHttpRequest request) {
		for ( IRoute route : routes ) {
			if ( route.isApplicatable(request) ) {
				route.applicate(request);
				return;
			}
		}
	}
}
