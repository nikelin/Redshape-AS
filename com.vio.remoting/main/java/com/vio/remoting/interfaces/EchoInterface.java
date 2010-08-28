package com.vio.remoting.interfaces;

import java.rmi.RemoteException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.remote.interfaces
 * @date May 4, 2010
 */
public interface EchoInterface extends RemoteInterface {

    public String test( String request ) throws RemoteException;

}
