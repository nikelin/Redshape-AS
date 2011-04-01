package tests.com.redshape.utils;

import com.redshape.utils.PackageLoader;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.ResourcesLoader;
import org.apache.log4j.Logger;
import org.junit.*;
import tests.mocks.mockpackage.Mock1;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 3:28:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class PackageLoaderTest {
    private static final Logger log = Logger.getLogger( PackageLoaderTest.class );

    @Test
    @Ignore
    public void testWithoutFiltering() {
        PackageLoader loader = new PackageLoader();
        ResourcesLoader resourcesLoader = new ResourcesLoader();

        try {
            Assert.assertArrayEquals( loader.getClasses("tests.mocks.mockpackage"), new Class[] {Mock1.class} );
        } catch ( PackageLoaderException e ) {
            Assert.fail("Unexpected loader exception");
        }

//@FIME: MVN Surefire classpath problem
//        try {
//            Assert.assertEquals( loader.<Object>getClasses("com.redshape.remoting.interfaces").length, 3 );
//        } catch( PackageLoaderException e ) {
//            Assert.fail("Unexpected loader exception during loading from target JAR path");
//        }
//
//        try {
//            Assert.assertEquals( loader.<Object>getClasses( resourcesLoader.loadFile("remoting.jar").getCanonicalPath(), "com.redshape.remoting.interfaces").length, 3 );
//        } catch( PackageLoaderException e ) {
//            Assert.fail("Unexpected loader exception during loading from target JAR path");
//        } catch ( IOException e ) {
//            Assert.fail("Cannot load testing resources");
//        }

    }

}
