package com.redshape.applications;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.config.IConfig;

public class SpringApplication extends AbstractApplication {
	private ClassPathXmlApplicationContext context;
	
	public SpringApplication( String args[] ) throws ApplicationException {
		super( args );
		
		if ( args.length < 1 ) {
			throw new ApplicationException("Too few startup parameters given");
		}
		
		this.context = new ClassPathXmlApplicationContext( args[0] );
	}
	
	@Override
	public void start() throws ApplicationException {
		this.setBootstrap( (IBootstrap) this.context.getBean("bootstrap") );
		this.setConfig( (IConfig) this.context.getBean("appConfig") );
	}
	
}
