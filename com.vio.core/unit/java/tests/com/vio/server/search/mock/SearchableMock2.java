package tests.com.vio.server.search.mock;

import com.vio.search.ISearchable;
import com.vio.search.annotations.AggregatedEntity;
import com.vio.search.annotations.Searchable;
import com.vio.search.annotations.SearchableField;
import com.vio.search.index.AggregationType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 4:14:37 PM
 * To change this template use File | Settings | File Templates.
 */
@Searchable( name = "searchable_mock_2" )
public class SearchableMock2 implements ISearchable {

    @AggregatedEntity( targetEntity = SearchableMock.class, type = AggregationType.ID, exclude = { @SearchableField( name = "searchable_mock_2" ) } )
    private ISearchable searchable = new SearchableMock();

    @AggregatedEntity( type = AggregationType.ID )
    private SearchableMock searchableMock;

    public Integer getId() {
        return 1;
    }
}
