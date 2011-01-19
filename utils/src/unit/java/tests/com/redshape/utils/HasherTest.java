package tests.com.vio.utils;

import org.junit.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 3:47:21 AM
 * To change this template use File | Settings | File Templates.
 */
public class HasherTest {

    @Test
    public void testHash() {
        return;
        // Assert.assertEquals( "Wrong hashing result", HasherFactory.getDefault().getHasher("SHA1").hash("d12345"), "0187a7c76a71c4db36b54dd3243ea00499293659" );
    }

    @Test
    public void testCheckEquality() {
        return;
        // Assert.assertTrue( "Wrong hash testing result", HasherFactory.getDefault().getHasher("SHA1").checkEquality( "d12345", HasherFactory.getDefault().getHasher("SHA1").hash("d12345") ) );
    }

}
