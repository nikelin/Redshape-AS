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

package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitResponse extends AbstractForkCommandResponse {
    public static final long ID = InitResponse.class.getCanonicalName().hashCode();

    public InitResponse(Status status) {
        super(ID, status);
    }

    @Override
    public void readFrom(DataInputStream stream) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeTo(DataOutputStream stream) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
