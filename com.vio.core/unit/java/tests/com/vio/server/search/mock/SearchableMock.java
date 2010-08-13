package tests.com.vio.server.search.mock;

import com.vio.search.ISearchable;
import com.vio.search.annotations.AggregatedEntity;
import com.vio.search.annotations.EnumerationField;
import com.vio.search.annotations.Searchable;
import com.vio.search.annotations.SearchableField;
import com.vio.search.index.AggregationType;
import com.vio.search.index.EnumerationType;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 13, 2010
 * Time: 4:11:47 PM
 * To change this template use File | Settings | File Templates.
 */
@Searchable( name = "searchable_mock_1" )
public class SearchableMock implements ISearchable {

    @SearchableField( name = "title" )
    private String title;

    @AggregatedEntity( targetEntity = SearchableMock2.class, type = AggregationType.COMPOSED )
    private ISearchable aggregated;

    @EnumerationField(EnumerationType.STRING)
    private EnumerationMock enumerated;

    public Integer getId() {
        return 1;
    }
}
