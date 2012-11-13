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

package com.redshape.persistence;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence
 * @date 2/6/12 {6:13 PM}
 */
public class DaoContextHolder implements ApplicationContextAware {
    private static final Object lock = new Object();
    private static DaoContextHolder instance;

    private ApplicationContext context;
    
    public DaoContextHolder() {
        synchronized (lock) {
            if ( instance != null ) {
                return;
            }

            instance = this;
        }
    }
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
    
    public ApplicationContext getContext() {
        return this.context;
    }
    
    public static final DaoContextHolder instance() {
        return instance;
    }
}