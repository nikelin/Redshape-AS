package com.redshape.render;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( value = RetentionPolicy.RUNTIME )
public @interface TargetEntity {

    Class<? extends IRenderable> entity();
}
