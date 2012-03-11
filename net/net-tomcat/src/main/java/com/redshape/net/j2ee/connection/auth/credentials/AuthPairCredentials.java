package com.redshape.net.j2ee.connection.auth.credentials;

import com.redshape.net.connection.auth.credentials.IServerCredentials;

/**
 * @author nikelin
 * @date 21:23
 */
public class AuthPairCredentials implements IServerCredentials {
    private String username;
    private String password;

    public AuthPairCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
