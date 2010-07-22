package com.vio.applications;

import com.vio.applications.bootstrap.IBootstrap;
import com.vio.config.IConfig;
import com.vio.utils.PackageLoader;
import com.vio.utils.ResourcesLoader;

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
