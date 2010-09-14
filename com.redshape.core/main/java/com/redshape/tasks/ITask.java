package com.redshape.tasks;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.tasks
 * @date Apr 5, 2010
 */
public interface ITask {
    public static Integer DEFAULT_EXECUTION_DELAY = 100;
    public static Integer DEFAULT_MAX_EXECUTION_DELAY = 1500;

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public enum State {
        COMPLETED,
        WAITING,
        ERROR
    }

    public void setPriority( Priority pririty );

    public Priority getPriority();

    public TaskResult execute() throws ExecutionException;

    public Integer getExecutionDelay();

    public Integer getMaxExecutionDelay();

    public boolean isPersistent();

}
