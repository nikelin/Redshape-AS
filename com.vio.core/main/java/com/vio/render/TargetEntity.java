package com.vio.render;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention( value = RetentionPolicy.RUNTIME )
public @interface TargetEntity {

    Class<? extends Renderable> entity();
}