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

package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.utils.StringUtils;
import com.redshape.utils.config.IConfig;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

public class SpringApplication extends AbstractApplication {
	public static String DEFAULT_CONTEXT_PATH = "context.xml";
	public static String SPRING_CONTEXT_PARAM = "as.contextPath";
	public static String IS_LOGS_DISABLED = "as.logsDisabled";

	private static final Logger log = Logger.getLogger( SpringApplication.class );
	private static ApplicationContext context;
	
	public SpringApplication( String args[] ) throws ApplicationException {
		super( args );

		context = this.loadContext( System.getProperty(SPRING_CONTEXT_PARAM, DEFAULT_CONTEXT_PATH ) );

		this.init();
	}
	
	protected void init() {
		this.setConfig( getContext().getBean( IConfig.class ) );
	}
	
	protected ApplicationContext loadContext( String contextPath ) {
        File file = new File(StringUtils.escapePath(contextPath) );
        if (file.exists()) {
            return new FileSystemXmlApplicationContext(contextPath);
        } else {
            return new ClassPathXmlApplicationContext(contextPath);
        }
    }
	
	public static ApplicationContext getContext() {
		return context;
	}
	
	@Override
	public void start() throws ApplicationException {
		try {
			context.getBean(IBootstrap.class).init();
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new ApplicationException( e.getMessage(), e );
		}
	}
	
}
