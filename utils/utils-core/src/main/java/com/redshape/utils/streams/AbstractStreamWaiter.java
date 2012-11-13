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

package com.redshape.utils.streams;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:23 PM}
 */
public abstract class AbstractStreamWaiter implements IStreamWaiter {

    private List<IStreamEventHandler> handlers = new ArrayList<IStreamEventHandler>();
    private InputStream stream;
    private Priority priority;
    
    AbstractStreamWaiter( InputStream stream ) {
        super();
        
        this.stream = stream;
    }
    
    protected List<IStreamEventHandler> getHandlers() {
        return this.handlers;
    }

    protected InputStream getStream() {
        return this.stream;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void addEventHandler(IStreamEventHandler handler) {
        this.handlers.add(handler);
    }

    @Override
    public void removeEventHandler(IStreamEventHandler handler) {
        this.handlers.remove(handler);
    }
}
