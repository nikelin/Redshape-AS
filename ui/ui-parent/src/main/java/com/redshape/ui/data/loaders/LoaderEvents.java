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

package com.redshape.ui.data.loaders;

import com.redshape.ui.data.DataEvents;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 21:40
 * To change this template use File | Settings | File Templates.
 */
public class LoaderEvents extends DataEvents {

    public LoaderEvents( String code ) {
        super( code );
    }

    public final static LoaderEvents BeforeLoad = new LoaderEvents("DataEvents.LoaderEvents.BeforeLoad");

    public final static LoaderEvents Loaded = new LoaderEvents("DataEvents.LoaderEvents.Loaded");

    public final static LoaderEvents Error = new LoaderEvents("DataEvents.LoaderEvents.Error");

}
