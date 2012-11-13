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

package com.redshape.forker.protocol.queue;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.utils.Commons;
import com.redshape.utils.IFilter;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class StandardProtocolQueue implements IProtocolQueue {

    private Queue<IForkCommand> commandsQueue = new LinkedBlockingQueue<IForkCommand>();
    private Queue<IForkCommandResponse> responsesQueue = new LinkedBlockingDeque<IForkCommandResponse>();

    @Override
    public boolean hasRequest() {
        return !this.commandsQueue.isEmpty();
    }

    @Override
    public boolean hasResponse() {
        return !this.responsesQueue.isEmpty();
    }

    @Override
    public void collectRequest(IForkCommand command) {
        this.commandsQueue.add(command);
    }

    @Override
    public IForkCommand peekRequest() {
        return this.commandsQueue.peek();
    }

    @Override
    public IForkCommand peekRequest(IFilter<IForkCommand> filter) {
        Commons.checkNotNull(filter);

        IForkCommand result = null;
        for ( IForkCommand command : this.commandsQueue ) {
            if ( !filter.filter(command) ) {
                continue;
            }

            result = command;
            break;
        }

        return result;
    }

    @Override
    public void collectResponse(IForkCommandResponse response) {
        this.responsesQueue.add(response);
    }

    @Override
    public IForkCommandResponse peekResponse() {
        return this.responsesQueue.peek();
    }

    @Override
    public IForkCommandResponse peekResponse(IFilter<IForkCommandResponse> filter) {
        Commons.checkNotNull(filter);

        IForkCommandResponse targetResponse = null;
        for ( IForkCommandResponse response : this.responsesQueue ) {
            if ( !filter.filter(response) ) {
                continue;
            }

            targetResponse = response;
            break;
        }

        return targetResponse;
    }

    @Override
    public IForkCommand pollRequest() {
        return this.commandsQueue.poll();
    }

    @Override
    public IForkCommandResponse pollResponse() {
        return this.responsesQueue.poll();
    }
}
