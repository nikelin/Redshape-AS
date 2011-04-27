package com.redshape.bindings.annotations;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Usable for as called conversion methods where constraint rule
 * for method prototype types must be equals to a target field not works where
 * this annotation said value writer to turn value consistency check off.
 *
 * For example:
 * class X....
 * private InetAddress x;
 *
 * public void setX( InetAddress address ) {}
 *
 * @IgnoreViolations
 * public void setX( String address )
 *
 * @see com.redshape.bindings.BeanInfo
 * @see com.redshape.bindings.BeanInfo.Accessors
 * @see com.redshape.bindings.annotations.BindableWriter
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.bindings.annotations
 */
@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD })
public @interface IgnoreViolations {

}
