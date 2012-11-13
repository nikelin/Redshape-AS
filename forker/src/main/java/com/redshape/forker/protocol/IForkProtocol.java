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

package com.redshape.forker.protocol;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.protocol
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {7:00 PM}
 */
public interface IForkProtocol {

    public enum TokenType {
        COMMAND,
        RESPONSE;
    }

    public TokenType matchToken() throws IOException;

    public <C extends IForkCommand> C readCommand() throws IOException;
    
    public void writeCommand( IForkCommand command ) throws IOException;
    
    public <R extends IForkCommandResponse> R readResponse() throws IOException;
    
    public void writeResponse( IForkCommandResponse response ) throws IOException;
    
}
