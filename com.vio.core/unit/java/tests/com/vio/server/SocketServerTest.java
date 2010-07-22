package tests.com.vio.server;

import com.vio.server.ApiServer;
import com.vio.server.ServerFactory;
import com.vio.server.adapters.socket.SocketAdapterFactory;
import org.junit.Assert;
import org.junit.Test;
import tests.mocks.socket.ServerSocketAdapterMock;
import tests.mocks.socket.SocketAdapterFactoryMock;

import javax.net.ServerSocketFactory;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 1:42:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class SocketServerTest {

    @Test
    public void testMain() {
        try {
            if ( true ) return;

            String host = "localhost";
            Integer port = 80;
            boolean isSSLEnabled = true;

            SocketAdapterFactory.setDefault( new SocketAdapterFactoryMock() );

            ApiServer server = ServerFactory.getInstance().createInstance( ApiServer.class, host, port, isSSLEnabled);

            Assert.assertEquals( server.getHost(), host );
            Assert.assertEquals( server.getPort(), port );
            Assert.assertEquals( server.isSSLEnabled(), isSSLEnabled );

            server.startListen();

            Assert.assertEquals( server.getSocketAdapter().getHost(), server.getHost() );
            Assert.assertEquals( server.getSocketAdapter().getPort(), server.getPort() );
            
        } catch ( Throwable e ) {
            Assert.fail();
        }
    }
}
