package com.redshape.search.collectors;

import com.redshape.search.*;
import com.redshape.search.index.IIndex;
import com.redshape.search.index.IIndexField;
import com.redshape.search.index.builders.IndexBuilder;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.lucene.document.Field;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 4:15:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardCollector implements IResultsCollector {
    private Set<CollectedItem> collected = new HashSet();

    public void collect( Class<? extends ISearchable> searchable, Field[] fields, String id ) {
        this.collected.add( new CollectedItem( searchable, fields, id ) );
    }

    public <T extends ISearchable> Collection<T> getResults() throws ProcessingException {
        try {
            Collection<T> results = new HashSet();

            for ( CollectedItem item : collected ) {
                Class<T> searchableClass = (Class<T>) item.getSearchable();

                IIndex index = IndexBuilder.newBuilder().getIndex(searchableClass);
                ISearchable searchable = searchableClass.newInstance();

                for ( Field field : item.getFields() ) {
                    IIndexField indexField = index.getField( field.name() );
                    if ( indexField == null ) {
                        throw new ProcessingException();
                    }

                    Object value;
                    if ( indexField.getSerializer() != null ) {
                        value = field.isBinary() ? Search.getSerializer( indexField.getSerializer() ).unserialize( field.getBinaryValue() ) : Search.getSerializer( indexField.getSerializer() ).unserialize( field.stringValue() );
                    } else {
                        value = field.stringValue();
                    }

                    PropertyUtils.setProperty( searchable, field.name(), value );
                }
            }

            return results;

        } catch ( ProcessingException e ) {
            throw e;
        } catch ( Throwable e ) {
            throw new ProcessingException();
        }
    }

    final static class CollectedItem {
        private Class<? extends ISearchable> searchable;
        private Field[] fields;
        private String id;

        public CollectedItem( Class<? extends ISearchable> searchable, Field[] fields, String id ) {
            this.searchable = searchable;
            this.id = id;
            this.fields = fields;
        }

        public Field[] getFields() {
            return this.fields;
        }

        public Class<? extends ISearchable> getSearchable() {
            return this.searchable;
        }

        public String getId() {
            return this.id;
        }
    }
}
