package com.vio.search.collectors;

import com.vio.persistence.entities.Entity;
import com.vio.persistence.managers.ManagerException;
import com.vio.persistence.managers.ManagersFactory;
import com.vio.search.ISearchable;
import com.vio.search.annotations.Collector;
import org.apache.lucene.document.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:06:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Collector( entityType = Entity.class )
public class HibernateCollector implements IResultsCollector {
    private Set<CollectedItem> collected = new HashSet<CollectedItem>();

    public void collect( Class<? extends ISearchable> searchable, Field[] fields, String id ) {
        this.collected.add( new CollectedItem( searchable, id ) );
    }

    public <T extends ISearchable> Collection<T> getResults() throws ProcessingException {
        try {
            Collection<T> results = new HashSet();
    
            for ( CollectedItem item : this.collected ) {
                results.add( (T) ManagersFactory.getDefault().getForEntity( item.getSearchable() ).find( Integer.valueOf( item.getId() ) ) );
            }

            return results;
        } catch ( ManagerException e ) {
            throw new ProcessingException();
        }
    }

    final static class CollectedItem {
        private Class<? extends ISearchable> searchable;

        private String id;

        public CollectedItem( Class<? extends ISearchable> searchable, String id ) {
            this.searchable = searchable;
            this.id = id;
        }

        public Class<? extends ISearchable> getSearchable() {
            return this.searchable;
        }

        public String getId() {
            return this.id;
        }
    }

}