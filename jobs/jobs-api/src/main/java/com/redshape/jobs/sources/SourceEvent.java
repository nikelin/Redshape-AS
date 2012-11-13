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

package com.redshape.jobs.sources;

import com.redshape.utils.events.AbstractEvent;

/**
 * Created by IntelliJ IDEA.
 * User: cyril
 * Date: 12/9/11
 * Time: 4:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class SourceEvent extends AbstractEvent {
    public static enum Type {
        SCHEDULED,
        CANCELLED,
        PAUSED
    }
    
    private Type type;
    
    public SourceEvent( Type type, Object... args) {
        super(args);
        
        this.type = type;
    }

    public Type getType() {
        return this.type;
    }
    
    
}
