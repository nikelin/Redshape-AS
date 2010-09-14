package com.redshape;

import com.redshape.remoting.interfaces.EchoInterface;
import com.redshape.remoting.interfaces.UsersInterface;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.util.logging.Logger;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio
 * @date May 4, 2010
 */
public final class RemotingMain {
    private static final Logger log = Logger.getLogger( RemotingMain.class.getCanonicalName() );

    public static void main( String arg[] ) throws Throwable {
        System.setProperty("java.security.policy", "security.policy");

        System.setSecurityManager( new RMISecurityManager() );
        EchoInterface echo = (EchoInterface) Naming.lookup("rmi://localhost:55239/EchoService");

        log.info( echo.test("2"));

        UsersInterface usersInterface = (UsersInterface) Naming.lookup("rmi://localhost:55239/UsersService");

        Integer uid = usersInterface.getOnlineStatus();
        log.info( String.valueOf( uid ) );
    }

}
