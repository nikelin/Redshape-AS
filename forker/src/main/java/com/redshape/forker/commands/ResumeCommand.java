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
