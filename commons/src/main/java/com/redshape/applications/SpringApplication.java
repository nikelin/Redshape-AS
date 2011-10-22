package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.utils.PackagesLoader;
import com.redshape.utils.ResourcesLoader;
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
		this.setBootstrap( getContext().getBean(IBootstrap.class) );
		this.setConfig( getContext().getBean( IConfig.class ) );
		this.setResourcesLoader( getContext().getBean( ResourcesLoader.class ) );
		this.setPackagesLoader( getContext().getBean( PackagesLoader.class ) );
	}
	
	protected ApplicationContext loadContext( String contextPath ) {
        File file = new File(contextPath);
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
			this.getBootstrap().init();
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new ApplicationException( e.getMessage(), e );
		}
	}
	
}
