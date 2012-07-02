package com.redshape.utils.config;

import com.redshape.utils.config.sources.IConfigSource;

import java.util.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.config
 * @date 10/20/11 1:13 PM
 */
public abstract class AbstractConfig implements IConfig {
    protected boolean nulled;
    protected String value;
    protected IConfig parent;
    protected String name;
    protected Map<String, String> attributes = new LinkedHashMap<String, String>();
    protected List<IConfig> childs = new ArrayList<IConfig>();
    protected IConfigSource source;

    protected boolean initialized;

    transient static protected ThreadLocal<Boolean> initializingThread = new ThreadLocal<Boolean>();

    transient protected final Lock lock = new ReentrantLock();
    transient protected Condition reloadCondition = lock.newCondition();

    protected AbstractConfig() {
        this(null, null, null);
    }

    protected AbstractConfig(IConfig parent, String name, String value) {
        this.parent = parent;
        this.name = name;
        this.value = value;
    }

    protected AbstractConfig(String name, String value) {
        this(null, name, value);
    }

    public AbstractConfig(IConfigSource source) throws ConfigException {
        this.source = source;
        this.init();
    }

    abstract protected void actualInit() throws ConfigException ;

    protected void init() throws ConfigException {
        this.lock.lock();
        try {
            this.initializingThread.set(true);
            this.initialized = false;
            this.actualInit();
            this.initialized = true;
        } finally {
            this.lock.unlock();
        }
    }

    protected void waitReady() {
        if ( this.initializingThread.get() != null
                && this.initializingThread.get() ) {
            return;
        }

        try {
            while ( !this.initialized ) {
                reloadCondition.await();
            }
        } catch ( Throwable e ) {
            try {
                reloadCondition.signal();
            } catch (Throwable ex ) {}
        }
    }

    protected void clear() {
        this.waitReady();
        this.value = null;
        this.name = null;
        this.parent = null;

        this.attributes.clear();
        this.childs.clear();
    }

    @Override
    public boolean isNull() {
        this.waitReady();
        return this.nulled;
    }

    @Override
    public String path() throws ConfigException {
        this.waitReady();
        List<String> route = new ArrayList<String>();
        IConfig parent = this;
        while ( null != parent ) {
            route.add( parent.name() );

            parent = parent.parent();
        }

        String[] routes = route.toArray( new String[route.size()] );
        StringBuilder result = new StringBuilder();
        for ( int i = routes.length; i > 0; i-- ) {
            result.append( routes[i] );

            if ( i != routes.length - 1 ) {
                result.append(".");
            }
        }

        return result.toString();
    }

    @Override
    public <T extends IConfig> List<T> childs() {
        this.waitReady();
        return (List<T>) this.childs;
    }

    @Override
    public boolean hasChilds() {
        this.waitReady();
        return !this.childs.isEmpty();
    }

    @Override
    public IConfig get(String name) throws ConfigException {
        this.waitReady();

        if (this.isNull()) {
            return this.createNull();
        }

        IConfig result = this;
        String[] pathNodes = name.split("\\.");
        if ( pathNodes.length == 1 ) {
            return this._get(name);
        }

        for ( String pathNode : pathNodes ) {
            result = result.get(pathNode);
            if ( result.isNull() ) {
                break;
            }
        }

        return result;
    }

    private IConfig _get( String name ) {
        for ( IConfig config : this.childs() ) {
            if ( config.name().equals(name) ) {
                return config;
            }
        }

        return this.createNull();
    }

    @Override
    public String[] list() {
        return this.list(null);
    }

    @Override
    public String[] list(String name) {
        this.waitReady();
        List<String> list = new ArrayList<String>();
        for ( IConfig node : this.childs() ) {
            if ( name == null || !node.name().equals(name) ) {
                continue;
            }

            list.add( node.value() );
        }

        return list.toArray( new String[ list.size() ] );
    }

    @Override
    public String name() {
        this.waitReady();
        return this.name;
    }

    @Override
    public String[] names() {
        this.waitReady();
        List<String> list = new ArrayList<String>();
        for ( IConfig node : this.childs() ) {
            list.add( node.name() );
        }

        return list.toArray( new String[ list.size() ] );
    }

    @Override
    public String attribute(String name) {
        this.waitReady();
        return this.attributes.get(name);
    }

    @Override
    public String[] attributeNames() {
        this.waitReady();
        return this.attributes.keySet().toArray( new String[this.attributes.size()] );
    }

    @Override
    public String value() {
        this.waitReady();
        return this.value;
    }

    @Override
    public IConfig parent() throws ConfigException {
        this.waitReady();
        return this.parent;
    }

    @Override
    public IConfig append(IConfig config) {
        this.waitReady();
        config.parent(this);
        this.childs.add(config);
        return this;
    }

    @Override
    public IConfig parent(IConfig config) {
        this.waitReady();
        this.parent = config;
        return this;
    }

    @Override
    public IConfig set(String value) throws ConfigException {
        this.waitReady();
        this.value = value;
        return this;
    }

    @Override
    public IConfig attribute(String name, String value) {
        this.waitReady();
        this.attributes.put(name, value);
        return this;
    }

    @Override
    public IConfig remove() throws ConfigException {
        this.waitReady();
        this.parent.remove(this);
        this.nulled = true;
        return this;
    }

    @Override
    public IConfig remove(IConfig config) throws ConfigException {
        this.waitReady();
        this.childs.remove(config);
        return this;
    }

    @Override
    public void save() throws ConfigException {
        this.waitReady();
        if ( this.source == null ) {
            throw new IllegalStateException("Associated holder not exists");
        }

        this.source.write( this.serialize() );
    }

    abstract protected IConfig createNull();
}
