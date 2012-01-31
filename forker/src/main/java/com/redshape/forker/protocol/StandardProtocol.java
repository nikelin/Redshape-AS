package com.redshape.forker.protocol;

import com.redshape.forker.Commands;
import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.utils.Commons;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.protocol
 * @date 1/31/12 {7:01 PM}
 */
public class StandardProtocol implements IForkProtocol {

    private static final long COMMAND_BEGIN = 0xA000F7;
    private static final long RESPONSE_BEGIN = 0xB000F8;

    @Override
    public IForkCommand readCommand(DataInputStream stream) throws IOException {
        Commons.checkNotNull(stream);

        /**
         * Read command start
         */
        while ( COMMAND_BEGIN != stream.readLong() ) {
            continue;
        }

        IForkCommand command;
        try {
            command = Commands.createCommand( stream.readLong() );
        } catch ( InstantiationException e ) {
            throw new IOException("Unable to construct command object");
        }

        command.readFrom( stream );

        return command;
    }

    @Override
    public void writeCommand(DataOutputStream stream, IForkCommand command) throws IOException {
        Commons.checkNotNull(stream);
        Commons.checkNotNull(command);
        
        stream.writeLong( COMMAND_BEGIN );
        command.writeTo( stream );
    }

    @Override
    public IForkCommandResponse readResponse(DataInputStream stream) throws IOException {
        Commons.checkNotNull(stream);

        while ( RESPONSE_BEGIN != ( stream.readLong() ) ) {
            continue;
        }

        IForkCommandResponse response;
        try {
            response = Commands.createResponse(
                stream.readLong(),
                IForkCommandResponse.Status.valueOf(stream.readUTF())
            );
        } catch ( InstantiationException e ) {
            throw new IOException( "Unable to construct response object", e );
        }

        response.readFrom(stream);

        return response;
    }

    @Override
    public void writeResponse(DataOutputStream stream, IForkCommandResponse response) throws IOException {
        Commons.checkNotNull(stream);
        Commons.checkNotNull(response);

        stream.writeLong( RESPONSE_BEGIN );
        response.writeTo(stream);
    }
}
