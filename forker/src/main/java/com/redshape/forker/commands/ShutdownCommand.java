package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {4:32 PM}
 */
public final class ShutdownCommand {

    public static class Request extends AbstractForkCommand {

        public Request() {
            super(Commands.SHUTDOWN, Commands.SHUTDOWN_RSP);
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
        }
    }

    public static class Response extends AbstractForkCommandResponse {

        public Response(Long id, Status status) {
            super(id, status);
        }

        @Override
        public void readFrom(DataInputStream stream) throws IOException {
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
        }
    }
}
