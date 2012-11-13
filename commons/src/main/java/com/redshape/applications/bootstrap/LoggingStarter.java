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

package com.redshape.applications.bootstrap;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.applications.bootstrap
 * @date 10/19/11 11:30 AM
 */
public class LoggingStarter extends AbstractBootstrapAction {

	public static void init() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");

		String path = System.getProperty("log4j.configuration", "log4j.properties");

		if ( path.endsWith( "xml" ) ) {
			DOMConfigurator.configure(path);
		} else {
			PropertyConfigurator.configure(path);
		}
	}

	@Override
	public void process() throws BootstrapException {

	}
}
