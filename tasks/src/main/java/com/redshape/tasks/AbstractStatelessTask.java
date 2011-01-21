package com.redshape.tasks;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date May 3, 2010
 */
public abstract class AbstractStatelessTask implements IStatelessTask {
    private Priority priority;
    private Map<String, Object> properties = new HashMap<String, Object>();

    public void setProperty( String name, Object value ) {
        this.properties.put( name, value);
    }

    public Object getProperty( String name ) {
        return this.properties.get(name);
    }

    public <T> List<T> getList( String property ) {
        return (List<T>) this.getProperty(property);
    }

    public Integer getInteger( String property ) {
        return (Integer) this.getProperty(property);
    }

    public String getString( String property ) {
        return (String) this.getProperty(property);
    }

    public boolean isPersistent() {
        return false;
    }

    public void setPriority( Priority priority ) {
        this.priority = priority;
    }

    public Priority getPriority() {
        return this.priority;
    }

    public Integer getExecutionDelay() {
        return IStatelessTask.DEFAULT_EXECUTION_DELAY;
    }

    public Integer getMaxExecutionDelay() {
        return IStatelessTask.DEFAULT_MAX_EXECUTION_DELAY;
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

}
