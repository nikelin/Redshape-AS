package com.vio.api.security;

import com.vio.applications.AccessibleApplication;
import com.vio.auth.AuthResult;
import com.vio.auth.AuthenticatorFactory;
import com.vio.auth.adapters.AuthenticatorInterface;
import com.vio.exceptions.ErrorCode;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.io.protocols.Constants;
import com.vio.io.protocols.core.VersionRegistryFactory;
import com.vio.io.protocols.vanilla.VanillaVersionsRegistry;
import com.vio.io.protocols.vanilla.request.IAPIRequest;
import com.vio.persistence.entities.IPAddress;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ISocketServer;
import com.vio.server.ServerException;
import com.vio.server.adapters.socket.client.ISocketAdapter;
import com.vio.server.policy.AbstractPolicy;
import com.vio.server.policy.IPolicy;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:37:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationPolicy extends AbstractPolicy<IAPIRequest, ISocketServer> {
    private final static Logger log = Logger.getLogger( AuthenticationPolicy.class );

    private ISocketServer server;

    public boolean applicate( IAPIRequest request ) {
        try {
            boolean checkResult = false;

            ISocketAdapter socket = request.getSocket();

            AuthenticatorInterface<IRequester> authenticator = AuthenticatorFactory.getInstance().getAdapter(IRequester.class);
            IRequester identity = authenticator.getIdentity( socket.getInetAddress() );
            if ( null == identity ) {
                String apiKey = (String) request.getHeader(
                        VersionRegistryFactory.getInstance(VanillaVersionsRegistry.class)
                            .getActualProtocol()
                            .getConstant(Constants.API_KEY_HEADER)
                ).getValue();
                IRequester resource = ( (AccessibleApplication) Registry.getApplication() ).createRequester();
                resource.setApiKey( apiKey );
                resource.setAddress( new IPAddress( socket.getInetAddress() ) );

                AuthResult<IRequester> result = authenticator.authenticate( resource );
                switch ( result.getStatus() ) {
                    case INVALID_IDENTITY:
                        this.setLastException( new ServerException( ErrorCode.EXCEPTION_INVALID_API_KEY ) );
                    break;

                    case FAIL:
                        this.setLastException( new ServerException( ErrorCode.EXCEPTION_AUTHENTICATION_FAIL ) );
                    break;

                    case INACTIVE_IDENTITY:
                        this.setLastException( new ServerException( ErrorCode.EXCEPTION_INACTIVE_API_KEY ) );
                    break;

                    case EXPIRED_IDENTITY:
                        this.setLastException( new ServerException( ErrorCode.EXCEPTION_EXPIRED_API_KEY ) );
                    break;

                    case SUCCESS:
                        identity = result.getIdentity();
                        checkResult = true;
                }
            } else {
                log.info("Session exists: restore..");
                checkResult = true;
            }

            if ( checkResult ) {
                identity.setLastAccessTime( new Date().getTime() );

                request.setIdentity(identity);

                log.info("Authenticated successful for key " + identity.getApiKey() );
            }

            return checkResult;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            return false;
        }
    }
}
