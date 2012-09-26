package com.redshape.forker;

import com.redshape.forker.handlers.IForkCommandHandler;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 12:13 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleCommandObject {

    public static class Handler implements IForkCommandHandler {

        @Override
        public boolean isSupported(IForkCommand command) {
            return command instanceof Command;
        }

        @Override
        public IForkCommandResponse execute(IForkCommand command) throws ProcessException {
            return new Response(IForkCommandResponse.Status.SUCCESS);
        }
    }

    public static class Command extends AbstractForkCommand {
        public static final long ID = Command.class.getCanonicalName().hashCode();

        public Command() {
            super(Command.ID, Response.ID);
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public static class Response extends AbstractForkCommandResponse {
        public static final long ID = Response.class.getCanonicalName().hashCode();

        public Response(Status status) {
            super(Response.ID, status);
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

}
