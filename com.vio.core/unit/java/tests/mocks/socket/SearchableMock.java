package tests.mocks.socket;

import com.vio.search.ISearchable;
import com.vio.search.annotations.Searchable;
import com.vio.search.annotations.SearchableField;
import com.vio.search.annotations.SearchableFieldSerializer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 3:57:45 PM
 * To change this template use File | Settings | File Templates.
 */
@Searchable( name = "searchable_mock" )
public class SearchableMock implements ISearchable {
    @SearchableField(
        analyzable = false,
        name = "id"
    )
    private Integer id = 23;

    @SearchableField(
       analyzable = true,
       binary = false,
       name = "name"
    )
    private String name = "Afla";

    public Integer getId() {
        return id;
    }

}
