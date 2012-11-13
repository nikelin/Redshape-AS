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

package com.redshape.persistence.migration.strategy.entities;

import com.redshape.persistence.migration.Action;
import com.redshape.persistence.migration.MigrationException;
import com.redshape.persistence.migration.MigrationManager;
import com.redshape.persistence.migration.Migrator;
import org.apache.log4j.Logger;

import java.util.Collection;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 26, 2010
 * Time: 1:07:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class Update extends AbstractEntitiesStrategy {
    private static final Logger log = Logger.getLogger( Update.class );

    public Update( MigrationManager manager ) {
        super(manager);
    }

    protected void processMigrators( Migrator[] migrators, int from, int to ) throws MigrationException {
       int delta = to - from;
       int version = to;

       Collection<Class<?>> processed = new Vector<Class<?>>();
       int activations = 0;
       while( activations++ < migrators.length && activations != delta ) {
           for ( Migrator migrator : migrators ) {
               if ( ( migrator.getVersion() <= version && migrator.getVersion() <= to && !processed.contains( migrator.getClass() )  ) && !migrator.isReductant( Action.UPDATE ) ) {
                   processed.add( migrator.getClass() );
                   migrator.setUp();
                   break;
               }
           }

           version += 1;
       }
    }

    public boolean isAffectable( int version, int from, int to ) {
        return from < to && version > from && version < to;
    }

}
