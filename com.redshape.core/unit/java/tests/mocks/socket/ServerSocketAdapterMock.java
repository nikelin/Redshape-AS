package tests.mocks.socket;

import com.redshape.server.adapters.socket.client.ISocketAdapter;
import com.redshape.server.adapters.socket.server.IServerSocketAdapter;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:47:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class ServerSocketAdapterMock implements IServerSocketAdapter {
    private String host;
    private int port;

    public ServerSocketAdapterMock() {}

    public ServerSocketAdapterMock( String host, int port ) {
        this.host = host;
        this.port = port;
    }

    @Override
    public void setHost(String host) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getHost() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setPort(int port) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Integer getPort() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ServerSocketChannel getChannel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InetAddress getInetAddress() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public ISocketAdapter accept() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void startListening() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
