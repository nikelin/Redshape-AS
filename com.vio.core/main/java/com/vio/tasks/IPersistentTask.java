package com.vio.tasks;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date May 3, 2010
 */
public interface IPersistentTask extends ITask {
    public static Integer DEFAULT_EXECUTION_DELAY = ITask.DEFAULT_EXECUTION_DELAY;
    public static Integer DEFAULT_MAX_EXECUTION_DELAY = ITask.DEFAULT_MAX_EXECUTION_DELAY;
        
    public void remove() throws Throwable;

    public void save() throws Throwable;

    public Integer getExecutionFails();    

    public void markFail();

    public Long getLastExecutionTime();

    public void setLastExecutionTime( Long timestamp );

    public void setId( Integer id );

    public Integer getId();

    public boolean isCompleted();    

}
