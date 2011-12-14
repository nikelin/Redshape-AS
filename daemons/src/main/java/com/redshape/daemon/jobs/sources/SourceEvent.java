package com.redshape.daemon.jobs.sources;

import com.redshape.utils.events.AbstractEvent;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SourceEvent extends AbstractEvent {
    public static enum Type {
        SCHEDULED,
        CANCELLED,
        PAUSED
    }
    
    private Type type;
    
    public SourceEvent( Type type, Object... args) {
        super(args);
        
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
    
    
}
