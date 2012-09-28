package com.redshape.io.net.auth;

import java.io.IOException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 11:36:03 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IKeyedCredentials extends ICredentials {

    public PublicKey getPublic() throws IOException;

    public PrivateKey getPrivate() throws IOException;

}
