package com.redshape.io.net.auth;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 8, 2010
 * Time: 11:53:57 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IFileKeyedCredentials extends IKeyedCredentials {

    public void init( File file );
    
}
