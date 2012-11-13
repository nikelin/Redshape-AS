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

package com.redshape.forker;

import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {6:02 PM}
 */
public abstract class AbstractForkCommandResponse implements IForkCommandResponse {
    
    private Long id;
    private Status status;
    private Long qualifier;

    protected AbstractForkCommandResponse(Long id, Status status) {
        Commons.checkNotNull(id);

        this.id = id;
        this.status = status;
    }

    @Override
    public Long getQualifier() {
        return qualifier;
    }

    @Override
    public void setQualifier(Long qualifier) {
        this.qualifier = qualifier;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }
}
