package com.redshape.applications;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.config.IConfig;

public class SpringApplication extends AbstractApplication {
	private ConfigurableApplicationContext context;
	
	public SpringApplication( String args[] ) throws ApplicationException {
		super( args );
		
		if ( args.length < 1 ) {
			throw new ApplicationException("Too few startup parameters given");
		}
		
		if ( !isUnderJar( this.getClass().getProtectionDomain().getCodeSource().getLocation().toString() ) ) {
			this.context = new ClassPathXmlApplicationContext( args[0] );
		} else {
			this.context = new FileSystemXmlApplicationContext( args[0] );
		}
	}
	
	@Override
	public void start() throws ApplicationException {
		this.setBootstrap( (IBootstrap) this.context.getBean("bootstrap") );
		this.setConfig( (IConfig) this.context.getBean("appConfig") );
	}
	
}
