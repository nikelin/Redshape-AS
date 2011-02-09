package com.redshape.io.net.auth;

/**
 * Functional interface to merge all credential types
 * into same family
 *
 * @author nikelin
 */
public interface IPasswordCredentials extends ICredentials {

    public String getPassword();

}
