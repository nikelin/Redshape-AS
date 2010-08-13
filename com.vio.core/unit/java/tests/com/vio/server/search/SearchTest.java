package tests.com.vio.server.search;

import com.vio.search.Search;
import org.junit.*;
import tests.com.vio.server.search.mock.SearchableMock;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:51:04 PM
 * To change this template use File | Settings | File Templates.
 */
public class SearchTest {

    @Test
    public void testMain() throws Throwable {
        if ( true ) return;
        
        Search.setEngine( Search.getLucene() );

        Search.search(
            Search.and(
                Search.eq( Search.literal("name"), Search.literal("afla!") ),
                Search.eq( Search.literal("age"), Search.literal("dfla!") )
            ),
            SearchableMock.class
        );
    }

}
