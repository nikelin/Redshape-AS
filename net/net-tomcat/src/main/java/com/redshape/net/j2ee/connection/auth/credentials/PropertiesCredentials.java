package com.redshape.net.j2ee.connection.auth.credentials;

import com.redshape.net.connection.auth.credentials.IServerCredentials;

/**
 * @author nikelin
 * @date 21:27
 */
public class PropertiesCredentials implements IServerCredentials {
    private String accessFile;
    private String passwordFile;

    public PropertiesCredentials(String accessFile, String passwordFile) {
        this.accessFile = accessFile;
        this.passwordFile = passwordFile;
    }

    public String getAccessFile() {
        return accessFile;
    }

    public String getPasswordFile() {
        return passwordFile;
    }
}
