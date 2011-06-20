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
	private static final Logger log = Logger.getLogger( SpringApplication.class );
	private static ApplicationContext context;
	
	public SpringApplication( String args[] ) throws ApplicationException {
		super( args );
		
		if ( args.length < 1 ) {
			throw new ApplicationException("Too few startup parameters given");
		}
		
		context = this.loadContext(args[0]);
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
