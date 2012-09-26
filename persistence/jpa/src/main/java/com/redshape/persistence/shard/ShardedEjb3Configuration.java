package com.redshape.persistence.shard;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.SettingsFactory;
import org.hibernate.ejb.Ejb3Configuration;
import org.hibernate.ejb.EventListenerConfigurator;
import org.hibernate.ejb.InjectionSettingsFactory;
import org.hibernate.proxy.EntityNotFoundDelegate;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.strategy.ShardStrategyFactory;

import javax.persistence.EntityNotFoundException;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.shard
 * @date 1/27/12 {4:49 PM}
 */
public class ShardedEjb3Configuration extends Ejb3Configuration {

    protected static class _Ejb3EntityNotFoundDelegate implements EntityNotFoundDelegate, Serializable {
        public void handleEntityNotFound(String entityName, Serializable id) {
            throw new EntityNotFoundException("Unable to find " + entityName  + " with id " + id);
        }
    }

    private static final _Ejb3EntityNotFoundDelegate entityNotFoundDelegateObject = new _Ejb3EntityNotFoundDelegate();

    private List<ShardConfiguration> shardConfigs;
    private ShardStrategyFactory shardStrategyFactory;
    private Map<Integer, Integer> shardsMap;
    
    public ShardedEjb3Configuration( List<ShardConfiguration> shardConfigs,
                                     ShardStrategyFactory shardStrategyFactory ) {
        this(shardConfigs, shardStrategyFactory, new HashMap<Integer, Integer>() );
    }

    public ShardedEjb3Configuration(  List<ShardConfiguration> shardConfigs,
                                      ShardStrategyFactory shardStrategyFactory,
                                      Map<Integer, Integer> shardsMap ) {
        Class<?> parentClazz = this.getClass().getSuperclass();
        
        SettingsFactory settingsFactory;
        this._securedSetFieldValue("settingsFactory",
                parentClazz,
                settingsFactory = new InjectionSettingsFactory());

        this.shardConfigs = shardConfigs;
        this.shardStrategyFactory = shardStrategyFactory;
        this.shardsMap = shardsMap;

        AnnotationConfiguration cfg;
        this._securedSetFieldValue("cfg",
                parentClazz,
                cfg = this.createConfiguration(settingsFactory));
        cfg.setEntityNotFoundDelegate( ShardedEjb3Configuration.entityNotFoundDelegateObject );
        this._securedSetFieldValue("listenerConfigurator",
                parentClazz,
                new EventListenerConfigurator(this));
    }

    protected void _securedSetFieldValue( String fieldName, Class<?> subject, Object value ) {
        try {
            Field field = subject.getDeclaredField(fieldName);
            if ( !field.getType().isAssignableFrom( value.getClass() ) ) {
                throw new IllegalArgumentException("Wrong value type for a field " + fieldName );
            }
            
            field.setAccessible(true);
            field.set( this, value );
            field.setAccessible(false);
        } catch ( Throwable e ) {
            if ( IllegalArgumentException.class.isAssignableFrom(e.getClass()) ) {
                throw (IllegalArgumentException) e;
            }

            throw new IllegalArgumentException( e.getMessage(), e );
        }
    }
    
    protected AnnotationConfiguration createConfiguration( SettingsFactory settingsFactory ) {
        return new ShardedConfiguration( settingsFactory,
                                        this.shardConfigs,
                                        this.shardStrategyFactory,
                                        this.shardsMap );
    }
}
