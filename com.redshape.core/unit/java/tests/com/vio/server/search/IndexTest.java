package tests.com.vio.server.search;

import com.redshape.search.index.IIndex;
import com.redshape.search.index.builders.BuilderException;
import com.redshape.search.index.builders.IIndexBuilder;
import com.redshape.search.index.builders.IndexBuilder;
import org.junit.Test;
import tests.com.vio.server.search.mock.SearchableMock;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 4:03:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexTest {

   @Test
   public void buildTest() {
       try {
           IIndexBuilder builder = IndexBuilder.newBuilder();

           IIndex index = builder.getIndex( SearchableMock.class );
//
//           assertTrue( index.hasField("aggregated") );
//           assertTrue( index.hasField("title") );

       } catch ( BuilderException e ) {
       } catch ( InstantiationException e ) {
       }

   }

}
