package com.vio.daemons;

import com.vio.auth.AuthenticatorFactory;
import com.vio.auth.Identity;
import com.vio.auth.adapters.AuthenticatorInterface;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ApiServer;
import com.vio.server.ISocketServer;
import com.vio.server.ServerFactory;
import com.vio.utils.Constants;
import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TimerTask;


/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 2:16:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class GarbageKillerTask extends TimerTask {
    private final static Logger log = Logger.getLogger( GarbageKillerTask.class );
    public static long PERIOD = Constants.TIME_MINUTE * 2;

    @Override
    public void run() {
        log.info("Cleaning up...");
        this.cleanAuthenticatedSessions();
    }
    
    protected void cleanAuthenticatedSessions() {
        log.info("Attempts to clean authenticated sessions registry...");

        ISocketServer server = ServerFactory.getInstance().getInstance(ApiServer.class);

        AuthenticatorInterface authenticator = AuthenticatorFactory.getInstance().getAdapter( IRequester.class );
        if ( server != null ) {
            Map<Object, Identity> authenticated = authenticator.getAuthenticated();
            for ( Object key : authenticated.keySet() ) {
                Identity identity = authenticated.get(key);
                if ( authenticator.isAuthExpired( identity ) ) {
                    authenticator.unauthorize( identity );
                }
            }

        } else {
            log.warn("Authenticator for API interface has not been started!");
        }
    }


}
