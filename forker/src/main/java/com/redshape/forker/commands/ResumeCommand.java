package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.Commands;

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

        public Request() {
            super( Commands.RESUME, Commands.RESUME_RSP );
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException { }

        @Override
        public void readFrom(DataInputStream input) throws IOException { }
    }

    public static class Response extends AbstractForkCommand {

        public Response(Long id, Long responseId) {
            super(id, responseId);
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException { }

        @Override
        public void readFrom(DataInputStream input) throws IOException { }
    }

}
