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

import com.redshape.commands.AbstractCommand;
import com.redshape.persistence.migration.MigrationManager;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 1, 2010
 * Time: 12:41:59 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMigrateCommand extends AbstractCommand {
    private MigrationManager manager;

    public AbstractMigrateCommand( MigrationManager manager ) {
        this.setManager(manager);
    }

    public void setManager( MigrationManager manager ) {
        this.manager = manager;
    }

    public MigrationManager getManager() {
        return this.manager;
    }

}
