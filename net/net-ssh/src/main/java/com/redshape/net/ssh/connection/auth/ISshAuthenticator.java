package com.redshape.net.ssh.connection.auth;

import com.redshape.net.connection.auth.IConnectionAuthenticator;
import net.schmizz.sshj.SSHClient;

/**
 * @author nikelin
 * @date 13:35
 */
public interface ISshAuthenticator extends IConnectionAuthenticator<SSHClient> {

}
