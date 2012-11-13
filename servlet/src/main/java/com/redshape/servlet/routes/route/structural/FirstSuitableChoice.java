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
