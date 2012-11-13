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
import com.redshape.utils.IFilter;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolQueue {

    public boolean hasRequest();

    public boolean hasResponse();

    public void collectRequest( IForkCommand command );

    public IForkCommand pollRequest();

    public IForkCommand peekRequest();

    public IForkCommand peekRequest( IFilter<IForkCommand> filter );

    public void collectResponse( IForkCommandResponse response );

    public IForkCommandResponse pollResponse();

    public IForkCommandResponse peekResponse();

    public IForkCommandResponse peekResponse( IFilter<IForkCommandResponse> filter );

}
