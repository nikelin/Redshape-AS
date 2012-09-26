package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {4:32 PM}
 */
public final class PauseCommand  {

    public static class Response extends AbstractForkCommandResponse {
        public static final long ID = Response.class.getCanonicalName().hashCode();

        public Response(Status status) {
            super(ID, status);
        }

        @Override
        public void readFrom(DataInputStream stream) throws IOException {
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
        }
    }

    public static class Request extends AbstractForkCommand {
        public static final long ID = Request.class.getCanonicalName().hashCode();

        public Request() {
            super( Request.ID, Response.ID );
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
        }
    }

}
