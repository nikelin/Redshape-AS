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

package com.redshape.forker.handlers;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IForkCommandHandler {

    public boolean isSupported( IForkCommand command );

    public IForkCommandResponse execute( IForkCommand command ) throws ProcessException;

}
