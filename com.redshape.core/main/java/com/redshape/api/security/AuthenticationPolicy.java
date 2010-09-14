package com.redshape.api.security;

import com.redshape.applications.AccessibleApplication;
import com.redshape.auth.AuthResult;
import com.redshape.auth.AuthenticatorFactory;
import com.redshape.auth.adapters.AuthenticatorInterface;
import com.redshape.exceptions.ErrorCode;
import com.redshape.io.protocols.core.Constants;
import com.redshape.io.protocols.core.VersionRegistryFactory;
import com.redshape.io.protocols.vanilla.VanillaVersionsRegistry;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.persistence.entities.IPAddress;
import com.redshape.persistence.entities.requesters.IRequester;
import com.redshape.server.ISocketServer;
import com.redshape.server.ServerException;
import com.redshape.server.adapters.socket.client.ISocketAdapter;
import com.redshape.server.policy.AbstractPolicy;
import com.redshape.utils.Registry;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:37:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationPolicy extends AbstractPolicy<IApiRequest, ISocketServer> {
    private final static Logger log = Logger.getLogger( AuthenticationPolicy.class );

    private ISocketServer server;

    @Override
    public boolean applicate( IApiRequest request ) {
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
