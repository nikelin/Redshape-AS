package tests.com.vio.server.search.mock;

import com.redshape.search.ISearchable;
import com.redshape.search.annotations.AggregatedEntity;
import com.redshape.search.annotations.EnumerationField;
import com.redshape.search.annotations.Searchable;
import com.redshape.search.annotations.SearchableField;
import com.redshape.search.index.AggregationType;
import com.redshape.search.index.EnumerationType;

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
