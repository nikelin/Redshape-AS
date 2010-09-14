package com.redshape.applications;

import java.io.IOException;

/**
 * Interface for applications that runs as a single process instance
 *
 * @author nikelin
 */
public interface ISingletonApplication extends IApplication {

    public boolean isRunning();

    public Integer getPid() throws IOException;
    
}
