package com.redshape.persistence.shard;

import org.apache.log4j.Logger;
import org.hibernate.AnnotationException;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.*;
import org.hibernate.cfg.beanvalidation.BeanValidationActivator;
import org.hibernate.engine.Mapping;
import org.hibernate.engine.SessionFactoryImplementor;
import org.hibernate.event.EventListeners;
import org.hibernate.event.PreInsertEventListener;
import org.hibernate.event.PreUpdateEventListener;
import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.shards.ShardId;
import org.hibernate.shards.cfg.ShardConfiguration;
import org.hibernate.shards.cfg.ShardedEnvironment;
import org.hibernate.shards.session.ShardedSessionFactory;
import org.hibernate.shards.session.ShardedSessionFactoryImpl;
import org.hibernate.shards.strategy.ShardStrategyFactory;
import org.hibernate.shards.util.Maps;
import org.hibernate.shards.util.Preconditions;
import org.hibernate.shards.util.Sets;
import org.hibernate.util.PropertiesHelper;
import org.hibernate.util.ReflectHelper;

import javax.persistence.OneToOne;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.shard
 * @date 1/27/12 {4:55 PM}
 */
public class ShardedConfiguration extends AnnotationConfiguration {
    private static final Logger log = Logger.getLogger(ShardedConfiguration.class);

    /**
     * Class name of the class needed to enable Search.
     */
    protected static final String SEARCH_STARTUP_CLASS = "org.hibernate.search.event.EventListenerRegister";

    private Mapping mappings;

    /**
     * Method to call to enable Search.
     */
    protected static final String SEARCH_STARTUP_METHOD = "enableHibernateSearch";

    private final List<ShardConfiguration> shardConfigs;

    // user-defined sharding behavior
    private final ShardStrategyFactory shardStrategyFactory;

    // maps virtual shard ids to physical shard ids
    private final Map<Integer, Integer> virtualShardToShardMap;

    // maps physical shard ids to sets of virtual shard ids
    private final Map<Integer, Set<ShardId>> shardToVirtualShardIdMap;

    public ShardedConfiguration( SettingsFactory sf, List<ShardConfiguration> shardConfigs,
                                 ShardStrategyFactory shardStrategyFactory ) {
        this(sf, shardConfigs, shardStrategyFactory, new HashMap<Integer, Integer>() );
    }
    
    public ShardedConfiguration(SettingsFactory sf, List<ShardConfiguration> shardConfigs,
                                ShardStrategyFactory shardStrategyFactory,
                                Map<Integer, Integer> shardsMap ) {
        super(sf);

        Preconditions.checkNotNull(shardConfigs);
        Preconditions.checkNotNull(shardsMap);
        Preconditions.checkArgument(!shardConfigs.isEmpty());
        Preconditions.checkNotNull(shardStrategyFactory);

        this.shardConfigs = shardConfigs;
        this.virtualShardToShardMap = shardsMap;
        this.shardStrategyFactory = shardStrategyFactory;
        
        if (!virtualShardToShardMap.isEmpty()) {
            // build the map from shard to set of virtual org.hibernate.shards
            shardToVirtualShardIdMap = Maps.newHashMap();
            for(Map.Entry<Integer, Integer> entry : virtualShardToShardMap.entrySet()) {
                Set<ShardId> set = shardToVirtualShardIdMap.get(entry.getValue());
                // see if we already have a set of virtual org.hibernate.shards
                if (set == null) {
                    // we don't, so create it and add it to the map
                    set = Sets.newHashSet();
                    shardToVirtualShardIdMap.put(entry.getValue(), set);
                }
                set.add(new ShardId(entry.getKey()));
            }
        } else {
            shardToVirtualShardIdMap = Maps.newHashMap();
        }
    }

    /**
     * @return A ShardedSessionFactory built from the prototype config and
     * the shard-specific configs passed into the constructor.
     */
    public ShardedSessionFactory buildShardedSessionFactory() {
        Map<SessionFactoryImplementor, Set<ShardId>> sessionFactories = Maps.newHashMap();
        // since all configs get their mappings from the prototype config, and we
        // get the set of classes that don't support top-level saves from the mappings,
        // we can get the set from the prototype and then just reuse it.
        Set<Class<?>> classesWithoutTopLevelSaveSupport =
                determineClassesWithoutTopLevelSaveSupport(this);
        for (ShardConfiguration config : shardConfigs) {
            populatePrototypeWithVariableProperties(config);
            // get the shardId from the shard-specific config
            Integer shardId = config.getShardId();
            if(shardId == null) {
                final String msg = "Attempt to build a ShardedSessionFactory using a "
                        + "ShardConfiguration that has a null shard id.";
                log.fatal(msg);
                throw new NullPointerException(msg);
            }
            Set<ShardId> virtualShardIds;
            if (virtualShardToShardMap.isEmpty()) {
                // simple case, virtual and physical are the same
                virtualShardIds = Collections.singleton(new ShardId(shardId));
            } else {
                // get the set of shard ids that are mapped to the physical shard
                // described by this config
                virtualShardIds = shardToVirtualShardIdMap.get(shardId);
            }
            sessionFactories.put(_buildSessionFactory(), virtualShardIds);
        }
        final boolean doFullCrossShardRelationshipChecking =
                PropertiesHelper.getBoolean(
                        ShardedEnvironment.CHECK_ALL_ASSOCIATED_OBJECTS_FOR_DIFFERENT_SHARDS,
                        this.getProperties(),
                        true);
        return
                new ShardedSessionFactoryImpl(
                        sessionFactories,
                        shardStrategyFactory,
                        classesWithoutTopLevelSaveSupport,
                        doFullCrossShardRelationshipChecking);
    }

    /**
     * @return the Set of mapped classes that don't support top level saves
     */
    @SuppressWarnings("unchecked")
    private Set<Class<?>> determineClassesWithoutTopLevelSaveSupport(Configuration config) {
        Set<Class<?>> classesWithoutTopLevelSaveSupport = Sets.newHashSet();
        for(Iterator<PersistentClass> pcIter = config.getClassMappings(); pcIter.hasNext(); ) {
            PersistentClass pc = pcIter.next();
            for(Iterator<Property> propIter = pc.getPropertyIterator(); propIter.hasNext(); ) {
                if(doesNotSupportTopLevelSave(propIter.next())) {
                    Class<?> mappedClass = pc.getMappedClass();
                    log.info(String.format("Class %s does not support top-level saves.", mappedClass.getName()));
                    classesWithoutTopLevelSaveSupport.add(mappedClass);
                    break;
                }
            }
        }
        return classesWithoutTopLevelSaveSupport;
    }

    /**
     * there may be other scenarios, but mappings that contain one-to-one mappings
     * definitely can't be saved as top-level objects (not part of a cascade and
     * no properties from which the shard can be inferred)
     */
    boolean doesNotSupportTopLevelSave(Property property) {
        return property.getValue() != null &&
                OneToOne.class.isAssignableFrom(property.getValue().getClass());
    }

    /**
     * Takes the values of the properties declared in VARIABLE_PROPERTIES from
     * a shard-specific config and sets them as the values of the same properties
     * in the prototype config.
     */
    void populatePrototypeWithVariableProperties(ShardConfiguration config) {
        safeSet(this, Environment.USER, config.getShardUser());
        safeSet(this, Environment.PASS, config.getShardPassword());
        safeSet(this, Environment.URL, config.getShardUrl());
        safeSet(this, Environment.DATASOURCE, config.getShardDatasource());
        safeSet(this, Environment.CACHE_REGION_PREFIX, config.getShardCacheRegionPrefix());
        safeSet(this, Environment.SESSION_FACTORY_NAME, config.getShardSessionFactoryName());
        safeSet(this, ShardedEnvironment.SHARD_ID_PROPERTY, config.getShardId().toString());
    }

    /**
     * Set the key to the given value on the given config, but only if the
     * value is not null.
     */
    static void safeSet(Configuration config, String key, String value) {
        if(value != null) {
            config.setProperty(key, value);
        }
    }

    /**
     * Helper function that creates an actual SessionFactory.
     */
    private SessionFactoryImplementor _buildSessionFactory() {
        return (SessionFactoryImplementor) this.buildSessionFactory();
    }

    protected void enableBeanValidation() {
        BeanValidationActivator.activateBeanValidation(getEventListeners(), getProperties());
    }

    @Override
    public Mapping buildMapping() {
        return this.mappings = super.buildMapping();
    }

    protected void enableLegacyHibernateValidator() {
        //add validator events if the jar is available
        boolean enableValidatorListeners = !"false".equalsIgnoreCase(
                getProperty(
                        "hibernate.validator.autoregister_listeners"
                )
        );
        Class validateEventListenerClass = null;
        try {
            validateEventListenerClass = ReflectHelper.classForName(
                    "org.hibernate.validator.event.ValidateEventListener",
                    AnnotationConfiguration.class
            );
        }
        catch ( ClassNotFoundException e ) {
            //validator is not present
            log.debug( "Legacy Validator not present in classpath, ignoring event listener registration" );
        }
        if ( enableValidatorListeners && validateEventListenerClass != null ) {
            //TODO so much duplication
            Object validateEventListener;
            try {
                validateEventListener = validateEventListenerClass.newInstance();
            }
            catch ( Exception e ) {
                throw new AnnotationException( "Unable to load Validator event listener", e );
            }
            {
                boolean present = false;
                PreInsertEventListener[] listeners = getEventListeners().getPreInsertEventListeners();
                if ( listeners != null ) {
                    for ( Object eventListener : listeners ) {
                        //not isAssignableFrom since the user could subclass
                        present = present || validateEventListenerClass == eventListener.getClass();
                    }
                    if ( !present ) {
                        int length = listeners.length + 1;
                        PreInsertEventListener[] newListeners = new PreInsertEventListener[length];
                        System.arraycopy( listeners, 0, newListeners, 0, length - 1 );
                        newListeners[length - 1] = ( PreInsertEventListener ) validateEventListener;
                        getEventListeners().setPreInsertEventListeners( newListeners );
                    }
                }
                else {
                    getEventListeners().setPreInsertEventListeners(
                            new PreInsertEventListener[] { ( PreInsertEventListener ) validateEventListener }
                    );
                }
            }

            //update event listener
            {
                boolean present = false;
                PreUpdateEventListener[] listeners = getEventListeners().getPreUpdateEventListeners();
                if ( listeners != null ) {
                    for ( Object eventListener : listeners ) {
                        //not isAssignableFrom since the user could subclass
                        present = present || validateEventListenerClass == eventListener.getClass();
                    }
                    if ( !present ) {
                        int length = listeners.length + 1;
                        PreUpdateEventListener[] newListeners = new PreUpdateEventListener[length];
                        System.arraycopy( listeners, 0, newListeners, 0, length - 1 );
                        newListeners[length - 1] = ( PreUpdateEventListener ) validateEventListener;
                        getEventListeners().setPreUpdateEventListeners( newListeners );
                    }
                }
                else {
                    getEventListeners().setPreUpdateEventListeners(
                            new PreUpdateEventListener[] { ( PreUpdateEventListener ) validateEventListener }
                    );
                }
            }
        }
    }

    /**
     * Tries to automatically register Hibernate Search event listeners by locating the
     * appropriate bootstrap class and calling the <code>enableHibernateSearch</code> method.
     */
    protected void enableHibernateSearch() {
        // load the bootstrap class
        Class searchStartupClass;
        try {
            searchStartupClass = ReflectHelper.classForName( SEARCH_STARTUP_CLASS, AnnotationConfiguration.class );
        }
        catch ( ClassNotFoundException e ) {
            // TODO remove this together with SearchConfiguration after 3.1.0 release of Search
            // try loading deprecated HibernateSearchEventListenerRegister
            try {
                searchStartupClass = ReflectHelper.classForName(
                        "org.hibernate.cfg.search.HibernateSearchEventListenerRegister", AnnotationConfiguration.class
                );
            }
            catch ( ClassNotFoundException cnfe ) {
                log.debug( "Search not present in classpath, ignoring event listener registration." );
                return;
            }
        }

        // call the method for registering the listeners
        try {
            Object searchStartupInstance = searchStartupClass.newInstance();
            Method enableSearchMethod = searchStartupClass.getDeclaredMethod(
                    SEARCH_STARTUP_METHOD,
                    EventListeners.class, Properties.class
            );
            enableSearchMethod.invoke( searchStartupInstance, getEventListeners(), getProperties() );
        }
        catch ( InstantiationException e ) {
            log.debug( "Unable to instantiate {}, ignoring event listener registration.", e );
        }
        catch ( IllegalAccessException e ) {
            log.debug( "Unable to instantiate {}, ignoring event listener registration.", e );
        }
        catch ( NoSuchMethodException e ) {
            log.debug( "Method enableHibernateSearch() not found in {}.", e );
        }
        catch ( InvocationTargetException e ) {
            log.debug( "Unable to execute {}, ignoring event listener registration.", e );
        }
    }

    protected void validate() throws MappingException {
        Iterator iter = classes.values().iterator();
        while ( iter.hasNext() ) {
            ( (PersistentClass) iter.next() ).validate( this.mappings );
        }
        iter = collections.values().iterator();
        while ( iter.hasNext() ) {
            ( (Collection) iter.next() ).validate( this.mappings );
        }
    }

    protected EventListeners getInitializedEventListeners() {
        EventListeners result = (EventListeners) getEventListeners().shallowCopy();
        result.initializeListeners( this );
        return result;
    }

    @Override
    public SessionFactory buildSessionFactory() throws HibernateException {
        enableLegacyHibernateValidator();
        enableBeanValidation();
        enableHibernateSearch();

        log.debug( "Preparing to build session factory with filters : " + filterDefinitions );
        secondPassCompile();
        validate();
        Environment.verifyProperties( this.getProperties() );
        Properties copy = new Properties();
        copy.putAll( this.getProperties() );
        PropertiesHelper.resolvePlaceHolders(copy);
        Settings settings = buildSettings( copy );

        return new SessionFactoryImpl(
                this,
                this.mappings,
                settings,
                getInitializedEventListeners(),
                getSessionFactoryObserver()
        );
    }
}
