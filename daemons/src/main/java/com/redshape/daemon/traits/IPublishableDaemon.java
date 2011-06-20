package com.redshape.daemon.traits;

import com.redshape.daemon.DaemonException;
import com.redshape.daemon.IDaemonAttributes;

import javax.jws.WebMethod;

public interface IPublishableDaemon<T, V extends IDaemonAttributes> 
								extends IDaemon<V> {

    /**
     * Returns true when someone remote calls it
     *
     * @return true
     */
    @WebMethod
    public boolean ping();

    /**
     * Returns com.redshape.daemon status when someone remote calls it
     *
     * @return
     */
    @WebMethod
    public String status();
    
    @WebMethod( exclude = true )
    public T getEndPoint();
    
    @WebMethod( exclude = true )
    public void publish() throws DaemonException;
    
    @WebMethod( exclude = true )
    public boolean doPublishing();
	
}
