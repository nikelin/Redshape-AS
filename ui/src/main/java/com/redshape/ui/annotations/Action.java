/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.ui.annotations;

import com.redshape.ui.events.UIEvents;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author nikelin
 */
@Retention( RetentionPolicy.RUNTIME )
@Target( ElementType.METHOD )
public @interface Action {

    public String eventType();

}
