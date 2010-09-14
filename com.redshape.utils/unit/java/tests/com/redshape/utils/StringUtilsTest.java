package tests.com.vio.utils;

import com.redshape.utils.StringUtils;
import org.junit.*;

import java.io.File;
import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 7, 2010
 * Time: 2:36:19 AM
 * To change this template use File | Settings | File Templates.
 */
public class StringUtilsTest {

    @Test
    public void testRepeat() {
        String initialString = "x ";
        String expectedString = "x x x x ";

        Assert.assertEquals("Expected and actual strings is not equals",
                            expectedString,
                            StringUtils.repeat( initialString, 4) );
    }

    @Test
    public void testReverse() {
        String initialString = "1234567";
        String expectedString = "7654321";

        Assert.assertEquals("Expected and actual strings is not equals",
                           expectedString,
                           StringUtils.reverse( initialString ) );

        Assert.assertArrayEquals( "Expected and actual char sequences is different",
                            expectedString.toCharArray(),
                            StringUtils.reverse( initialString.toCharArray() ) );
    }

    @Test
    public void testToCamelCase() {
        String initialString = "add_my_numbers";

        String expectedString;
        // Without uc_first
        {
            expectedString = "addMyNumbers";
            Assert.assertEquals("Expected and actual strings is not equals",
                                expectedString,
                                StringUtils.toCamelCase( initialString, false ) );
        }

        // with uc_first
        {
            expectedString = "AddMyNumbers";
            Assert.assertEquals("Expected and actual strings is not equals",
                                expectedString,
                                StringUtils.toCamelCase( initialString, true ) );
        }
    }

    @Test
    public void testFromCamelCase() {
        String initialStrng = "AddMyNumbers";

        String expectedString;
        {
            expectedString = "add_my_numbers";

            Assert.assertEquals("Expected and actual strings is not equals",
                                expectedString,
                                StringUtils.fromCamelCase( initialStrng, "_" ) );
        }
    }

    @Test
    public void testJoin() {
        Assert.assertEquals("Expected and actual strings is not equals",
                            "1,2,3",
                            StringUtils.join( new Integer[] { 1, 2, 3 }, "," ) );

        Assert.assertEquals("Expected and actual strings is not equals",
                            "1,2,3",
                            StringUtils.join(Arrays.asList( new Integer[] { 1, 2, 3 } ), "," ) );
    }

    @Test
    public void testGetFileExtension() {
        Assert.assertEquals("Expected and actual results is different",
                            "jpg",
                            StringUtils.getFileExtension("d/nikelin/1.jpg") );
        
        Assert.assertEquals("Expected and actual results is different",
                            "jpg",
                            StringUtils.getFileExtension( new File("d/nikelin/1.jpg") ) );
    }

}
