package com.redshape.applications;

import org.apache.log4j.Logger;

/**
 * Abstract realization of accessible application
 *
 * @author nikelin
 * @project vio
 * @package com.vio.applications
 * @date Apr 19, 2010
 */
public abstract class AccessibleApplication extends AbstractApplication implements IAccessibleApplication {
    @SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( AccessibleApplication.class );

    public AccessibleApplication( String[] args ) throws ApplicationException {
        super( args );
    }

}
