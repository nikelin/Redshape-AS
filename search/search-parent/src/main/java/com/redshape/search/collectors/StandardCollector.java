package com.redshape.search.collectors;

import com.redshape.search.ISearchFacade;
import com.redshape.search.index.IIndexField;
import com.redshape.search.index.builders.IIndexBuilder;
import com.redshape.search.serializers.ISerializersFacade;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.beans.IntrospectionException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:15:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardCollector implements IResultsCollector {
    private static final Logger log = Logger.getLogger( StandardCollector.class );

	@Autowired( required = true )
	private ISerializersFacade serializersFacade;

	@Autowired( required = true )
	private IIndexBuilder indexBuilder;

	@Autowired( required = true )
	private ISearchFacade searchFacade;

    private Set<CollectedItem> collected = new HashSet();

	public ISerializersFacade getSerializersFacade() {
		return serializersFacade;
	}

	public void setSerializersFacade(ISerializersFacade serializersFacade) {
		this.serializersFacade = serializersFacade;
	}

	public IIndexBuilder getIndexBuilder() {
		return indexBuilder;
	}

	public void setIndexBuilder(IIndexBuilder indexBuilder) {
		this.indexBuilder = indexBuilder;
	}

	public ISearchFacade getSearchFacade() {
		return searchFacade;
	}

	public void setSearchFacade(ISearchFacade searchFacade) {
		this.searchFacade = searchFacade;
	}

	public void collect( Class<?> searchable, Map<IIndexField, Object> fields, String id ) {
        this.collected.add( new CollectedItem( searchable, fields, id ) );
    }

    public <T> Collection<T> getResults() throws ProcessingException {
        try {
            Collection<T> results = new HashSet<T>();
            for ( CollectedItem item : this.collected ) {
                Class<T> searchableClass = (Class<T>) item.getSearchable();
                T searchable = searchableClass.newInstance();
                if ( !item.getId().equals("null") ) {
                    try {
                        Property property = PropertyUtils.getInstance().getProperty(searchableClass, "id");
                        property.set( searchable, Long.valueOf( item.getId() ) );
                    } catch ( IntrospectionException e ) {
                        log.error( e.getMessage(), e );
                    }
                }

				Map<IIndexField, Object> fields = item.getFields();
                for ( IIndexField field : fields.keySet() ) {
                    PropertyUtils.getInstance().getProperty(searchable.getClass(), field.getFieldName())
											   .set( searchable, fields.get(field));
                }

				results.add( searchable );
            }

            return results;
        } catch ( Throwable e ) {
            throw new ProcessingException( e.getMessage(), e );
        }
    }

    final static class CollectedItem {
        private Class<?> searchable;
        private Map<IIndexField, Object> fields;
        private String id;

        public CollectedItem( Class<?> searchable, Map<IIndexField, Object> fields, String id ) {
            this.searchable = searchable;
            this.id = id;
            this.fields = fields;
        }

        public Map<IIndexField, Object> getFields() {
            return this.fields;
        }

        public Class<?> getSearchable() {
            return this.searchable;
        }

        public String getId() {
            return this.id;
        }
    }
}
