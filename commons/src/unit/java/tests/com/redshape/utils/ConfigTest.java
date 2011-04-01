package tests.com.redshape.utils;

import com.redshape.utils.config.XMLConfig;
import com.redshape.utils.config.ConfigException;
import com.redshape.utils.config.IConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 10, 2010
 * Time: 1:39:43 AM
 * To change this template use File | Settings | File Templates.
 */
public class ConfigTest {
    private IConfig config;

    @Before
    public void testMainConfig() throws ConfigException {
        this.config = new XMLConfig( "test.config.xml" );
    }

    @Test
    public void testMain() throws ConfigException {
        assertNotNull( config.get("server") );

        assertNotNull( config.get("server").get("host") );

        assertEquals( "localhost", config.get("server").get("host").value() );
        assertNotNull( config.get("server").get("port") );
        assertEquals( "80", config.get("server").get("port").value() );
        assertNotNull( config.get("api") );
        assertEquals( "31", config.get("api").attribute("x") );
        assertArrayEquals( new String[]{ "host", "port" }, config.get("server").names() );
        assertArrayEquals( new String[] { "localhost", "80" }, config.get("server").list() );
    }

}
