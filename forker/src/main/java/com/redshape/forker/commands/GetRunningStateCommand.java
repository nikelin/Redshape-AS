package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;
import com.redshape.forker.IForkCommandResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {6:07 PM}
 */
public final class GetRunningStateCommand {
    
    public static class Request extends AbstractForkCommand {

        public Request() {
            super(Commands.GET_RUNNING_STATE, Commands.GET_RUNNING_STATE_RSP );
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
        }
    }
    
    public static class Response extends AbstractForkCommandResponse {

        private boolean state;

        public Response( Long id, IForkCommandResponse.Status status ) {
            super(id, status);
        }

        public boolean getState() {
            return state;
        }

        @Override
        public void readFrom(DataInputStream stream) throws IOException {
            this.state = stream.readBoolean();
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
            stream.writeBoolean( this.state );
        }
    }
    
}
