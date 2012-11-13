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
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorResponse extends AbstractForkCommandResponse {
    public static final long ID = ErrorResponse.class.getCanonicalName().hashCode();

    private String details;

    public ErrorResponse(String details, Status status) {
        super(ID, status);

        this.details = details;
    }

    @Override
    public void readFrom(DataInputStream stream) throws IOException {
        this.details = stream.readUTF();
    }

    @Override
    public void writeTo(DataOutputStream stream) throws IOException {
        stream.writeUTF( this.details );
    }
}
