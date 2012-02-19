package com.redshape.utils;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 2/17/12 {5:59 PM}
 */
public interface ILambda<T> {

    public T invoke() throws InvocationException;

    public T invoke( Object... object ) throws InvocationException;

    public ILambda<T> pass( Object... object );

}
