package tests.com.vio.server.search.mock;

import com.redshape.search.ISearchable;
import com.redshape.search.annotations.AggregatedEntity;
import com.redshape.search.annotations.Searchable;
import com.redshape.search.annotations.SearchableField;
import com.redshape.search.index.AggregationType;

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
