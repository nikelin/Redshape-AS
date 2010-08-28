package com.vio.remoting.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.remoting.annotations
 * @date May 4, 2010
 */
@Retention( value = RetentionPolicy.RUNTIME )
public @interface RemoteService {

    public String name();
}
