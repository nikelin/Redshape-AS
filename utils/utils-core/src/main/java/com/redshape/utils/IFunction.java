package com.redshape.utils;

import java.lang.reflect.Method;

public interface IFunction<V, T> extends ILambda<T> {

    public void bind( V context );

    public Method toMethod();

}
