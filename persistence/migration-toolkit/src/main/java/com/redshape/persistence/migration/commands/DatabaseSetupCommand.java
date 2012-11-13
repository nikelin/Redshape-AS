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
import com.redshape.commands.ExecutionException;
import com.redshape.commands.annotations.Command;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 27, 2010
 * Time: 3:38:08 PM
 * To change this template use File | Settings | File Templates.
 */
@Command( module = "migrate", name = "rebuild-schema", helpMessage = "rebuild database schema")
public class DatabaseSetupCommand extends AbstractCommand {
    private static final Logger log = Logger.getLogger( DatabaseSetupCommand.class );

    @Override
    public void process() throws ExecutionException {
        /**
         * @FIXME: due to DAO refactoring
         */
    }

    @Override
    public boolean isSupports( String action ) {
        return false;
    }

    @Override
    public String[] getSupported() {
        return new String[] {};
    }

}
