package com.redshape.remoting.interfaces;

import java.rmi.RemoteException;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.remoting.interfaces
 * @date May 5, 2010
 */
public interface UsersInterface extends RemoteInterface {

    /**
     * Get user id by given session id
     * @param sessionId
     * @return Integer
     * @throws java.rmi.RemoteException
     */
    public Integer getUidBySid( Integer sessionId ) throws RemoteException;

    /**
     * Get user id related to session with given token
     * @param token
     * @return Integer
     * @throws RemoteException
     */
    public Integer getUidBySessionToken( String token ) throws RemoteException;

    /**
     * Update user presence status
     *
     * @param userId
     * @param presenceStatus
     * @return void
     * @throws RemoteException 
     */
    public void updatePresenceStatus( Integer userId, Integer presenceStatus ) throws RemoteException;

    /**
     * Get ordinal value for online status
     * @return
     * @throws RemoteException
     */
    public Integer getOnlineStatus() throws RemoteException;

    /**
     * Get ordinal value for offline status
     * @return
     * @throws RemoteException
     */
    public Integer getOfflineStatus() throws RemoteException;

    /**
     * Get ordinal value for broadcast status
     * @return
     * @throws RemoteException
     */
    public Integer getBroadcastStatus() throws RemoteException;

    public Integer getInactiveStatus() throws RemoteException;

    public Boolean isVerified( Integer userId, Integer subjectId ) throws RemoteException;

    public Boolean isProfileVerified( Integer userId ) throws RemoteException;

    public Boolean isSnapshotVerified( Integer userId ) throws RemoteException;

    public Boolean isPersonalVerified( Integer userId ) throws RemoteException;
}
