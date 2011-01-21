package tests.com.vio.utils;

import com.redshape.utils.InterfacesFilter;
import org.junit.*;
import tests.mocks.Mock1;
import tests.mocks.Mock2;
import tests.mocks.MockAnnotation1;
import tests.mocks.MockInterface1;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 3:03:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class InterfacesFilterTest {

    @Test
    public void testFilter() {
        InterfacesFilter filter = new InterfacesFilter( new Class[] { MockInterface1.class }, new Class[] { MockAnnotation1.class } );

        Assert.assertTrue( "Wrong filtering result for Mock1", filter.filter( Mock1.class ) );
        Assert.assertTrue( "Wrong filtering result for Mock2", filter.filter( Mock2.class ) );
        Assert.assertFalse( "Wrong filtering result for Mock2", filter.filter( Mock2.class, false ) );
        Assert.assertTrue( "Wrong filtering result for Mock1", filter.filter( Mock1.class, false ) );
    }

}