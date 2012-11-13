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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {4:38 PM}
 */
public abstract class AbstractForkCommand implements IForkCommand {
    
    private Long commandId;
    private Long responseId;
    private Long qualifier;
    
    protected AbstractForkCommand( Long id, Long responseId ) {
        Commons.checkNotNull(id);
        Commons.checkNotNull(responseId);

        this.responseId = responseId;
        this.commandId = id;
    }

    @Override
    public void setQualifier(Long value) {
        this.qualifier = value;
    }

    @Override
    public Long getQualifier() {
        return this.qualifier;
    }

    @Override
    public Long getResponseId() {
        return this.responseId;
    }

    @Override
    public Long getCommandId() {
        return this.commandId;
    }

}
