package com.redshape.net.ssh.connection.auth.credentials;

import com.redshape.net.connection.auth.credentials.IServerCredentials;

/**
 * @author nikelin
 * @date 14:11
 */
public class PublicKeyCredentials implements IServerCredentials {
    private String username;

    public PublicKeyCredentials(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
