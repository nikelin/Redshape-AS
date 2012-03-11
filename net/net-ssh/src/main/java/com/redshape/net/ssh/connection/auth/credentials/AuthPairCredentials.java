package com.redshape.net.ssh.connection.auth.credentials;

import com.redshape.net.connection.auth.credentials.IServerCredentials;

/**
 * @author nikelin
 * @date 16:39
 */
public class AuthPairCredentials implements IServerCredentials {
    private String login;
    private String password;

    public AuthPairCredentials(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
