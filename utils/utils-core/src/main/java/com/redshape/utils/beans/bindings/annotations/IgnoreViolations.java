/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils.beans.bindings.annotations;

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
 * @see com.redshape.utils.beans.bindings.BeanInfo
 * @see com.redshape.utils.beans.bindings.BeanInfo.Accessors
 * @see com.redshape.utils.beans.bindings.annotations.BindableWriter
 * @author nikelin
 * @date 25/04/11
 * @package com.redshape.bindings.annotations
 */
@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target( { ElementType.METHOD })
public @interface IgnoreViolations {

}
