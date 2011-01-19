package com.redshape.persistence.entities.payment.impl.currency;

import com.redshape.payments.ICurrencyFactory;
import com.redshape.payments.ICurrencyAmount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: May 10, 2010
 * Time: 11:02:31 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CurrencyFactory<T extends ICurrencyAmount, V extends Number> implements ICurrencyFactory<T, V> {
    private static ICurrencyFactory defaultInstance;
    private static Map<Class<? extends ICurrencyFactory>, ICurrencyFactory> factories = new HashMap<Class<? extends ICurrencyFactory>, ICurrencyFactory>();

    public static void setDefault( ICurrencyFactory instance ) {
        defaultInstance = instance;
    }

    public static ICurrencyFactory getDefault() {
        return defaultInstance;
    }

    public static ICurrencyFactory getInstance( Class<? extends ICurrencyFactory> clazz ) throws InstantiationException {
        ICurrencyFactory instance = factories.get(clazz);
        if ( instance != null ) {
            return instance;
        }

        try {
            instance = clazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        factories.put( clazz, instance );

        return instance;
    }

}
