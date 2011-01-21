package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;
import com.redshape.config.IConfig;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio
 * @date Apr 19, 2010
 */
public interface IApplication {

    public void setConfig( IConfig config );
    
    public IConfig getConfig();
    
    public void setEnvArg( String name, String value );

    public String getEnvArg( String name );

    public void start() throws ApplicationException;

    public void stop();

}
