package com.redshape.utils.streams;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:23 PM}
 */
public abstract class AbstractStreamWaiter implements IStreamWaiter {

    private List<IStreamEventHandler> handlers = new ArrayList<IStreamEventHandler>();
    private InputStream stream;
    private Priority priority;
    
    AbstractStreamWaiter( InputStream stream ) {
        super();
        
        this.stream = stream;
    }
    
    protected List<IStreamEventHandler> getHandlers() {
        return this.handlers;
    }

    protected InputStream getStream() {
        return this.stream;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void addEventHandler(IStreamEventHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void removeEventHandler(IStreamEventHandler handler) {
        this.handlers.remove(handler);
    }
}
