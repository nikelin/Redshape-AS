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

package com.redshape.persistence.migration.commands;

import com.redshape.commands.ExecutionException;
import com.redshape.persistence.migration.DataMigrationManager;
import com.redshape.persistence.migration.MigrationException;
import com.redshape.commands.annotations.Command;
import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 11:07:09 AM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "data", helpMessage = "databases data migration from X to Y")
public class DataMigrateCommand extends AbstractMigrateCommand {
    private static final Logger log = Logger.getLogger( DataMigrateCommand.class );

    private static final String to = "to";
    private static final String from = "from";

    private static final String[] PROPERTIES = { to, from };

    public DataMigrateCommand() {
        super( DataMigrationManager.getDefault() );
    }

    @Override
    public void process() throws ExecutionException {
         try {
            this.getManager().migrate( this.getIntegerProperty( from ), this.getIntegerProperty( to ) );
        } catch ( MigrationException e ) {
            log.error( e.getMessage(), e );
            throw new ExecutionException();
        }
    }

    @Override
    public String[] getImportant() {
        return new String[] { to, from };
    }

    @Override
    public String[] getSupported() {
        return PROPERTIES;
    }

    @Override
    public boolean isSupports( String name ) {
        return Arrays.binarySearch( PROPERTIES, name ) != -1;
    }
}
