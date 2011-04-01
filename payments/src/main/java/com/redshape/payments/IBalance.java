package com.redshape.payments;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 5:22:32 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IBalance<T extends ICurrencyAmount<T, V>, V extends Number> extends ICurrencyAmount<T, V> {

    public Set<? extends IBalanceOperation<T, V>> getOperations();

}
