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

package com.redshape.persistence.migration.strategy.fixtures;

import com.redshape.persistence.migration.MigrationException;
import com.redshape.persistence.migration.strategy.MigrationStrategy;

import com.redshape.persistence.entities.IEntity;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 3:38:01 PM
 * To change this template use File | Settings | File Templates.
 */
public class Update implements MigrationStrategy<Iterable<IEntity>> {
    private static final Logger log = Logger.getLogger( Update.class );

    // @FIXME: due to DAO refactoring
    public void execute( Iterable<IEntity> objects, int from, int to ) throws MigrationException {
        try {
            for ( IEntity entity : objects ) {
                   log.info("Saving entity " + entity.getClass().getName() + "...");
//                   entity.getDAO().save(entity, true);
            }
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new MigrationException();
        }
    }

    public boolean isAffectable( int version, int from, int to ) {
        return from < to && version > from && version < to; 
    }

}
