package com.vio.messaging;

import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.messaging
 * @date May 3, 2010
 */
public interface IMessageRespond {

    public Boolean isSuccessful();

    public void markSuccess( Boolean status );

    public Map<String, Object> getParams();
}
