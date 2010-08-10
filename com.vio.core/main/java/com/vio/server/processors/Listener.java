package com.vio.server.processors;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 18, 2010
 * Time: 4:44:46 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class Listener extends Thread {
    private boolean is_valid = true;

    public Listener() {}

    public Listener( ThreadGroup group ) {
        super(group, "");
    }

    /**
     * Validate current listener workflow
     * 
     * @return boolean
     */
    public boolean isValid() {
        return this.is_valid;
    }

    public void setInvalid( boolean bool ) {
        this.is_valid = !bool;
    }

    abstract public String getProcessName();
}
