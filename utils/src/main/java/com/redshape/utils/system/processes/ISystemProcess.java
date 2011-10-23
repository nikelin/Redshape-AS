package com.redshape.utils.system.processes;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

/**
 * @author nikelin
 * @date 20:27
 */
public interface ISystemProcess extends Serializable {

    public String readStdError() throws IOException;

    public String readStdInput() throws IOException;

    public InputStream getInputStream();

    public InputStream getErrorStream();

    public OutputStream getOutputStream();

    public boolean isSuccessful() throws IOException;

    public int waitFor() throws InterruptedException;

    public int exitValue();

    public void destroy();

    public int getPID();
}
