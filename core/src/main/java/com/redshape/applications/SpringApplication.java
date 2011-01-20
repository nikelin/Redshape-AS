package com.redshape.applications;

import org.apache.log4j.Logger;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.config.IConfig;

public class SpringApplication extends AbstractApplication {
	private static final Logger log = Logger.getLogger( SpringApplication.class );
	private ConfigurableApplicationContext context;
	
	public SpringApplication( String args[] ) throws ApplicationException {
		super( args );
		
		if ( args.length < 1 ) {
			throw new ApplicationException("Too few startup parameters given");
		}
		
		this.context = new ClassPathXmlApplicationContext( args[0] );
	}
	
	@Override
	public void start() throws ApplicationException {
		try {
			IBootstrap boot = (IBootstrap) this.context.getBean("bootstrap"); 
			boot.init();
			
			this.setBootstrap( boot );
			this.setConfig( (IConfig) this.context.getBean("config") );
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
			throw new ApplicationException( e.getMessage(), e );
		}
	}
	
}
