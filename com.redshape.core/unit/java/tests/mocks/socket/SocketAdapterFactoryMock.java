package tests.mocks.socket;

import com.redshape.server.adapters.socket.SocketAdapterFactory;
import com.redshape.server.adapters.socket.client.ISocketAdapter;
import com.redshape.server.adapters.socket.server.IServerSocketAdapter;

import java.net.Socket;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:44:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketAdapterFactoryMock extends SocketAdapterFactory {

    public IServerSocketAdapter createServerSocketAdapter( String host, int port ) {
        return new ServerSocketAdapterMock( host, port );
    }

    public IServerSocketAdapter createSSLServerSocketAdapter( String host, int port) {
        return new SSLServerSocketAdapterMock( host, port );
    }

    public ISocketAdapter createSocketAdapter( Socket socket ) {
        return new SocketAdapterMock();
    }

}
