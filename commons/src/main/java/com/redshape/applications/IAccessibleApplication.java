package com.redshape.applications;

import com.redshape.api.requesters.IRequester;


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
