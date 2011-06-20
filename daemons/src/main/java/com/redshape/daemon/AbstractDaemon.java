package com.redshape.daemon;

import com.redshape.daemon.traits.IDaemon;
import com.redshape.utils.config.IConfig;
import com.redshape.utils.events.AbstractEventDispatcher;

import java.util.HashMap;
import java.util.Map;

/**
 * @author nikelin
 */
public abstract class AbstractDaemon<T extends IDaemonAttributes> 
					extends AbstractEventDispatcher 
					implements IDaemon<T> {
    private Map<T, DaemonAttribute> attributes = new HashMap<T, DaemonAttribute>();
    private IConfig config;
    private DaemonState state;

    @Override
    public void setAttribute( T name, Object value ) {
        if ( !this.attributes.containsKey(name) ) {
            this.attributes.put( name, new DaemonAttribute( String.valueOf( name ), value) );
        } else {
            this.attributes.get(name).setValue( value );
        }
    }

    @Override
	public <V> V getAttribute( T name ) {
        if ( this.attributes.containsKey(name) ) {
            return (V) this.attributes.get( name ).getObjectValue();
        }

        return null;
    }

    @Override
    public DaemonAttribute[] getAttributes() {
        return this.attributes.values().toArray( new DaemonAttribute[ this.attributes.size()] );
    }

    protected IConfig getConfig() {
        return this.config;
    }

    public void setConfig( IConfig config ) {
        this.config = config;
    }

    @Override
    public DaemonState getState() {
        return this.state;
    }

    @Override
    public void changeState( DaemonState state ) {
        this.state = state;
    }

}
