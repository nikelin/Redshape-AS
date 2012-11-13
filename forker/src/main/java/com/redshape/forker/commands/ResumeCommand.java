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

import com.redshape.forker.AbstractForkCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {4:32 PM}
 */
public final class ResumeCommand  {

    public static class Request extends AbstractForkCommand {
        public static final long ID = Request.class.getCanonicalName().hashCode();

        public Request() {
            super( Request.ID, Response.ID);
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException { }

        @Override
        public void readFrom(DataInputStream input) throws IOException { }
    }

    public static class Response extends AbstractForkCommand {
        public static final long ID = Response.class.getCanonicalName().hashCode();

        public Response() {
            super(Request.ID, Response.ID);
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException { }

        @Override
        public void readFrom(DataInputStream input) throws IOException { }
    }

}
