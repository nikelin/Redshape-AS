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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:52 PM}
 */
public interface IForkCommand {

    public void setQualifier( Long value );

    public Long getQualifier();

    /**
     * Return identifier which will be used by a protocol serializer
     * to indicates command response object in process output stream.
     *
     * @return
     */
    public Long getResponseId();

    /**
     * Return identifier which will be used by a protocol serializer
     * to indicates command object in process input stream.
     * @return
     */
    public Long getCommandId();

    public void readFrom( DataInputStream input ) throws IOException;

    public void writeTo( DataOutputStream output ) throws IOException;

}
