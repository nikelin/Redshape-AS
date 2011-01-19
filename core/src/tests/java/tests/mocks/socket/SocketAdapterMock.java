package tests.mocks.socket;

import com.redshape.server.adapters.socket.client.ISocketAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:47:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketAdapterMock implements ISocketAdapter {

    private boolean keepAlive;

    @Override
    public InetAddress getInetAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch ( Throwable e ) {
            return null;
        }
    }

    @Override
    public InetAddress getLocalAddress() {
        try {
            return InetAddress.getLocalHost();
        } catch ( Throwable e ) {
            return null;
        }
    }

    @Override
    public int getPort() {
        return 5674;
    }

    @Override
    public int getLocalPort() {
        return 54209;
    }

    @Override
    public SocketAddress getRemoteSocketAddress() {
        return null;
    }

    @Override
    public SocketAddress getLocalSocketAddress() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public SocketChannel getChannel() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getTcpNoDelay() throws SocketException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getSoLinger() throws SocketException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getOOBInline() throws SocketException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSoTimeout(int i) throws SocketException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getSoTimeout() throws SocketException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setSendBufferSize(int i) throws SocketException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getSendBufferSize() throws SocketException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setReceiveBufferSize(int i) throws SocketException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getReceiveBufferSize() throws SocketException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setKeepAlive(boolean b) throws SocketException {
        this.keepAlive = b;
    }

    @Override
    public boolean getKeepAlive() throws SocketException {
        return this.keepAlive;
    }

    @Override
    public int getTrafficClass() throws SocketException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getReuseAddress() throws SocketException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shutdownInput() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void shutdownOutput() throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isConnected() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isBound() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isClosed() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isInputShutdown() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isOutputShutdown() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

	@Override
	public Date getCreated() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCreated(Date date) {
		// TODO Auto-generated method stub
		
	}
}
