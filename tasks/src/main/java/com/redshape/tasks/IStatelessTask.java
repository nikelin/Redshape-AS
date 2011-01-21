package com.redshape.tasks;

import java.util.Map;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date May 3, 2010
 */
public interface IStatelessTask extends ITask {
    public static Integer DEFAULT_EXECUTION_DELAY = ITask.DEFAULT_EXECUTION_DELAY;
    public static Integer DEFAULT_MAX_EXECUTION_DELAY = ITask.DEFAULT_MAX_EXECUTION_DELAY;

    public void setProperty( String name, Object value );

    public Object getProperty( String name );

    public boolean isSupportedProperty( String name );

    public Map<String, Object> getProperties();
    
}
