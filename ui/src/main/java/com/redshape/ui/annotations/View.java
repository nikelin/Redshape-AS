package com.redshape.ui.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 07.01.11
 * Time: 13:11
 * To change this template use File | Settings | File Templates.
 */
@Retention( RetentionPolicy.RUNTIME )
public @interface View {

    public String title();

}
