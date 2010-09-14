package com.redshape.render;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.render
 * @date Apr 16, 2010
 */
@Retention( value = RetentionPolicy.RUNTIME )
public @interface Handler {

    public Class<?> type();

}
