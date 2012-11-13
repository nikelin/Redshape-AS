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

package com.redshape.utils.config.watchers;

import com.redshape.utils.config.sources.IConfigSource;
import com.redshape.utils.events.IEventDispatcher;

import java.util.Collection;
import java.util.concurrent.ExecutorService;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 7/2/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISourceWatcher extends IEventDispatcher {

    public void addWatchable( IConfigSource source );

    public void removeWatchable( IConfigSource source );

    public Collection<IConfigSource> getWatchableList();

    public void clearWatchableList();

    public void stop();

    public void start();

}
