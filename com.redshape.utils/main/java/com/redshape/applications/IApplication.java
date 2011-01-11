package com.redshape.applications;

import com.redshape.applications.bootstrap.IBootstrap;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio
 * @date Apr 19, 2010
 */
public interface IApplication {

    public Integer getCurrentVersion();

    public String getExecutionMode();

    public String getEnvArg( String name );

    public IBootstrap getBootstrap();

    public void start() throws ApplicationException;

    public void stop();

}
