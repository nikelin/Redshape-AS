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
