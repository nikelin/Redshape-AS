package com.redshape.validators;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 11, 2010
 * Time: 1:44:55 PM
 * To change this template use File | Settings | File Templates.
 */
abstract public class ValidatorsFactory {
    private static ValidatorsFactory defaultInstance = new DefaultValidatorsFactory();
    private static Map<Class<? extends ValidatorsFactory>, ValidatorsFactory> factories = new HashMap<Class<? extends ValidatorsFactory>, ValidatorsFactory>();
    private Map<Class<? extends IValidator>, IValidator> validators = new HashMap<Class<? extends IValidator>, IValidator>();
    private Map<Class<?>, IValidator> entities = new HashMap<Class<?>, IValidator>();

    public static ValidatorsFactory getDefault() {
        return defaultInstance;
    }

    public static ValidatorsFactory createInstance( Class<ValidatorsFactory> clazz ) throws InstantiationException, IllegalAccessException {
        ValidatorsFactory instance = factories.get(clazz);
        if ( instance != null ) {
            return instance;
        }

        instance = clazz.newInstance();

        factories.put( clazz, instance );

        return instance;
    }

    public IValidator getForEntity( Class<?> entity ) throws InstantiationException {
        IValidator v = this.entities.get(entity);
        if ( v != null ) {
            return v;
        }

        v = this.forEntity( entity );

        this.entities.put( entity, v );

        return v;
    }

    abstract protected IValidator forEntity( Class<?> entity ) throws InstantiationException;

    public IValidator getValidator( Class<? extends IValidator> clazz ) {
        try {
            IValidator v = this.validators.get(clazz);
            if ( v != null ) {
                return v;
            }

            v = clazz.newInstance();

            this.validators.put( clazz, v );

            return v;
        } catch ( Throwable e ) {
            return null;
        }
    }
}
