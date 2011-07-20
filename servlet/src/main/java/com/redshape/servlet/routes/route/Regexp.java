package com.redshape.servlet.routes.route;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.routes.IRoute;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regexp implements IRoute {
	private static final Logger log = Logger.getLogger( Regexp.class );
	
	private Pattern pattern;
	private Integer controllerGroup;
	private Integer actionGroup;
	
	public Regexp( String expression, Integer controllerGroup, 
									  Integer actionGroup ) {
		if ( expression == null || expression.isEmpty() ) {
			throw new IllegalArgumentException("<null> expression");
		}
		
		if ( controllerGroup == null ) {
			throw new IllegalArgumentException("<null> controller group");
		}
		
		if ( actionGroup == null ) {
			throw new IllegalArgumentException("<null> action group");
		}
		
		this.pattern = Pattern.compile(expression);
		this.controllerGroup = controllerGroup;
		this.actionGroup = actionGroup;
	}

	@Override
	public boolean isApplicatable(IHttpRequest request) {
		log.info("URI requested:" + request.getRequestURI() ); 
		log.info("URI pattern:" + this.pattern.pattern() );
		return this.pattern.matcher( request.getRequestURI() ).find();
	}

	@Override
	public void applicate(IHttpRequest request) {
		String sourcePath = request.getRequestURI();
		Matcher matcher = this.pattern.matcher( sourcePath );
		
		if ( matcher.find() ) {
			log.info( "Groups founded:" + matcher.groupCount() );
			log.info( matcher.group( this.actionGroup ) );
			request.setAction( sourcePath.substring( matcher.start( this.actionGroup ),
													 matcher.end( this.actionGroup ) )
                                    .replaceAll("\\/", "") );
			request.setController( sourcePath.substring( matcher.start( this.controllerGroup ),
													 matcher.end( this.controllerGroup ) )
                                    .replaceAll("\\/", "") );
		} else {
			throw new IllegalArgumentException();
		}
	}
	
}
