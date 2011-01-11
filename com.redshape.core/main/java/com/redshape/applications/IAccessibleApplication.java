package com.redshape.applications;

import com.redshape.persistence.entities.requesters.IRequester;

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
