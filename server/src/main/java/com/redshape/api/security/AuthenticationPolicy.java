package com.redshape.api.security;

import com.redshape.api.ErrorCodes;
import com.redshape.api.requesters.IRequester;
import com.redshape.applications.AccessibleApplication;
import com.redshape.applications.IAccessibleApplication;
import com.redshape.auth.AuthResult;
import com.redshape.auth.AuthenticatorFactory;
import com.redshape.auth.adapters.AuthenticatorInterface;
import com.redshape.io.net.adapters.socket.client.ISocketAdapter;
import com.redshape.io.protocols.core.Constants;
import com.redshape.io.protocols.core.VersionRegistryFactory;
import com.redshape.io.protocols.vanilla.VanillaVersionsRegistry;
import com.redshape.io.protocols.vanilla.request.IApiRequest;
import com.redshape.io.server.ServerException;
import com.redshape.io.server.policy.AbstractPolicy;
import com.redshape.io.server.policy.ApplicationResult;
import com.redshape.persistence.entities.IPAddress;
import com.redshape.server.ISocketServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 3:37:10 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationPolicy extends AbstractPolicy<IApiRequest> {
    private final static Logger log = Logger.getLogger( AuthenticationPolicy.class );
    
    @Autowired( required = true )
    private IAccessibleApplication application;
    
    public void setApplication( IAccessibleApplication application ) {
    	this.application = application;
    }
    
    protected IAccessibleApplication getApplication() {
    	return this.application;
    }

    @Override
    public ApplicationResult applicate( IApiRequest request ) {
        ApplicationResult result = new ApplicationResult( ApplicationResult.Flags.UNSUCCESSFUL );

        log.info("Client request authentication ...");
        try {
            ISocketAdapter socket = request.getSocket();

            AuthenticatorInterface<IRequester> authenticator = AuthenticatorFactory.getDefault().getAdapter(IRequester.class);
            IRequester identity = authenticator.getIdentity( socket.getInetAddress() );
            if ( null == identity ) {
                String apiKey = (String) request.getHeader(
                        VersionRegistryFactory.getInstance(VanillaVersionsRegistry.class)
                            .getActualProtocol()
                            .getConstant(Constants.API_KEY_HEADER)
                ).getValue();
                log.info("Client key: " + apiKey );

                IRequester resource = this.getApplication().createRequester();
                resource.setApiKey( apiKey );
                resource.setAddress( new IPAddress( socket.getInetAddress() ) );

                AuthResult<IRequester> authResult = authenticator.authenticate( resource );
                switch ( authResult.getStatus() ) {
                    case INVALID_IDENTITY:
                       result.setException( new ServerException( ErrorCodes.EXCEPTION_INVALID_API_KEY ) );
                    break;

                    case FAIL:
                        result.setException( new ServerException( ErrorCodes.EXCEPTION_AUTHENTICATION_FAIL ) );
                    break;

                    case INACTIVE_IDENTITY:
                        result.setException( new ServerException( ErrorCodes.EXCEPTION_INACTIVE_API_KEY ) );
                    break;

                    case EXPIRED_IDENTITY:
                        result.setException( new ServerException( ErrorCodes.EXCEPTION_EXPIRED_API_KEY ) );
                    break;

                    case SUCCESS:
                        request.setIdentity(authResult.getIdentity());
                        result.markSuccessful();
                }
            } else {
                log.info("Session exists: restore..");
                result.markSuccessful();
            }

            return result;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            result.setException(e);
            return result;
        }
    }
}
