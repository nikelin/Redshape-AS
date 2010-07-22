package com.vio.applications;

import com.vio.config.IConfig;
import com.vio.config.IServerConfig;
import com.vio.persistence.entities.requesters.IRequester;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio
 * @date Apr 19, 2010
 */
public interface IAccessibleApplication extends IApplication {

    public IRequester createRequester();

}
