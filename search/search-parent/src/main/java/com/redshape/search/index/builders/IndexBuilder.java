/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.search.index.builders;

import com.redshape.search.annotations.Searchable;
import com.redshape.search.index.IIndex;
import com.redshape.search.index.Index;
import com.redshape.search.index.visitor.VisitorException;
import com.redshape.utils.beans.Property;
import com.redshape.utils.beans.PropertyUtils;
import org.apache.log4j.Logger;

import java.beans.IntrospectionException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 4:06:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class IndexBuilder extends AbstractIndexBuilder {
    private static final Logger log = Logger.getLogger( IndexBuilder.class );

    private Map< Class<?>, IIndex> indexes = new HashMap();

    private boolean isSupported( Class<?> subject ) {
		return subject.getAnnotation(Searchable.class) != null;
	}

	protected void checkAssertions( Class<?> subject ) {
		if ( subject == null ) {
			throw new IllegalArgumentException("<null>");
		}

		if ( !this.isSupported(subject) ) {
			throw new IllegalArgumentException("Object is not supported");
		}
	}

    @Override
    public IIndex getIndex( Class<?> searchable ) throws BuilderException {
		this.checkAssertions(searchable);

        IIndex index = this.indexes.get( searchable);
        if ( index != null ) {
            return index;
        }

        index = this.buildIndex( searchable );

        this.indexes.put( searchable, index );

        return index;
    }

    protected IIndex buildIndex( Class<?> searchable ) throws BuilderException {
        try {
            IIndex index = new Index();

            Searchable meta = searchable.getAnnotation( Searchable.class );
            if ( meta == null ) {
                return null;
            }

            index.setName( meta.name() );

            for ( Property property : PropertyUtils.getInstance().getProperties(searchable) ) {
                try {
                    this.getFieldVisitor().visitField( index, searchable, property.getName() );
                } catch ( VisitorException e ) {
                    log.error("Index builder exception", e );
                    throw new BuilderException( e.getMessage(), e );
                }
            }

            return index;
        } catch ( IntrospectionException e ) {
            throw new BuilderException( e.getMessage(), e);
        }
    }

}
