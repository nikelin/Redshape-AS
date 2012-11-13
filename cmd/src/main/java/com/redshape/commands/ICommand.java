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

package com.redshape.commands;

import com.redshape.applications.bootstrap.IBootstrapAction;

import java.io.Writer;
import java.util.Collection;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 21, 2010
 * Time: 5:29:06 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ICommand {

    public void setWriter( Writer writer );

    public void process() throws ExecutionException;

    public boolean hasProperty( String name );

    public void setProperty( String name, String value );

    public Map<String, String> getProperties();

    public String getProperty( String name );

    public boolean isSupports( String name );

    public String[] getSupported();

    public String[] getImportant();

    public boolean isValid();

    public Collection<? extends IBootstrapAction> getBootstrapRequirements();

}
