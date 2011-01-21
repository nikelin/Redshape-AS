package com.redshape.server.policy;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 30, 2010
 * Time: 2:41:19 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ApplicationResult {
    public static enum Flags {
        UNSUCCESSFUL,
        SUCCESSFUL,
        VOID,
        EXCEPTION
    }

    private Flags state;

    private Throwable exceptionObject;

    public ApplicationResult() {
        this( Flags.UNSUCCESSFUL );
    }

    public ApplicationResult( Flags flag ) {
        this.state = flag;
    }

    /**
         * Check has been successfully finished
         * @return
         */
    public boolean isSuccessful() {
        return this.state.equals( Flags.SUCCESSFUL );
    }

    /**
         *  Check has been crashed by unexpected failure
         * @return
         */
    public boolean isException() {
        return this.state.equals( Flags.EXCEPTION );
    }

    /**
         *  There is no applicable policies to the given result
         * @return
         */
    public boolean isVoid() {
        return this.state.equals( Flags.VOID );
    }

    public void markVoid() {
        this.state = Flags.VOID;
    }

    public void markSuccessful() {
        this.state = Flags.SUCCESSFUL;
    }
    
    public void markUnsuccessful() {
        this.state = Flags.UNSUCCESSFUL;
    }

    /**
         * Set exception object
         * @param e
         */
    public void setException( Throwable e ) {
        this.state = Flags.EXCEPTION;
        this.exceptionObject = e;
    }

    /**
         * Return exception object in a case of positive result of isException() method
         * @return
         */
    public Throwable getException() {
        return this.exceptionObject;
    }

}
