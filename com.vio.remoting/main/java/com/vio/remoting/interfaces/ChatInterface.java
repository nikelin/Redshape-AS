package com.vio.remoting.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.remoting.interfaces
 * @date May 5, 2010
 */
public interface ChatInterface extends RemoteInterface {

    public Boolean isAllowed( Integer chatId, Integer userId ) throws RemoteException;

    public Boolean isOwner( Integer chatId, Integer userId ) throws RemoteException;
}
