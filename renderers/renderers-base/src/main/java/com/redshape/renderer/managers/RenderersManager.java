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

package com.redshape.renderer.managers;

import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.managers
 * @date 2/22/12 {2:15 AM}
 */
public final class RenderersManager {
    private static final RenderersManager instance = new RenderersManager();
    
    public static RenderersManager getInstance() {
        return instance;
    }
    
    private Stack<Object> invalid = new Stack<Object>();
    
    public boolean isValid( Object object ) {
        return this.invalid.contains(object);
    }
    
    public void addInvalid( Object object ) {
        this.invalid.add(object);
    }

    public Stack<?> getInvalid() {
        return this.invalid;
    }
    
    public void markValid( Object object ) {
        this.invalid.remove(object);
    }
    
    
}
