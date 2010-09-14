package com.redshape.tasks;

import com.redshape.messaging.MessageRespond;

import java.util.Map;
import java.util.HashMap;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date May 3, 2010
 */
public class TaskResult extends MessageRespond {
    private Map<String, Object> properties = new HashMap<String, Object>();
    private TaskExecutionStatus executionStatus;
    private Boolean successful;

    public void setExecutionStatus( TaskExecutionStatus status ) {
        this.executionStatus = status;
    }

    public TaskExecutionStatus getExecutionStatus() {
        return this.executionStatus;
    }

    public Map<String, Object> getParams() {
        return this.properties;
    }

    public void setParam( String name, Object value ) {
        this.properties.put( name, value );
    }

    public Object getParam( String name ) {
        return this.properties.get(name);
    }

    public Boolean isSuccessful() {
        return this.successful;
    }

    public void markSuccess( Boolean status ) {
        this.successful = status;
    }
}
