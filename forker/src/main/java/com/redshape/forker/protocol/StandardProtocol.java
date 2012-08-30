package com.redshape.forker.protocol;

import com.redshape.forker.Commands;
import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.utils.Commons;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.Arrays;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.protocol
 * @date 1/31/12 {7:01 PM}
 */
public class StandardProtocol implements IForkProtocol {

    private static final Logger log = Logger.getLogger( StandardProtocol.class );

    private static final Object readLock = new Object();
    private static final Object writeLock = new Object();

    private static final long COMMAND_BEGIN = 0xAFF0F7;
    private static final long RESPONSE_BEGIN = 0xBFF0F8;

    private DataInputStream inputStream;
    private DataOutputStream outputStream;


    public StandardProtocol(DataInputStream inputStream,
                            DataOutputStream outputStream) {
        Commons.checkNotNull(inputStream);
        Commons.checkNotNull(outputStream);

        this.outputStream = outputStream;
        this.inputStream = inputStream;
    }

    protected void waitAvailability( OutputStream stream ) throws IOException {
        boolean available = false;
        do {
            try {
                stream.write(0);
                stream.flush();
                available = true;
            } catch ( IOException e ) {
                if ( !e.getMessage().equals("Stream closed") ) {
                    break;
                }
            }
        } while ( !available );
    }

    protected void waitAvailability( InputStream stream ) throws IOException {
        while ( stream.available() <= 0 ) {
            try {
                log.info("Stream data not available, waiting for 1 second...");
                Thread.sleep(1000);
            } catch ( InterruptedException e ) {
                break;
            }
        }
    }

    @Override
    public TokenType matchToken() throws IOException {
        long token = this.matchStreamToken(RESPONSE_BEGIN, COMMAND_BEGIN);
        if ( token == COMMAND_BEGIN ) {
            return TokenType.COMMAND;
        } else if ( token == RESPONSE_BEGIN ) {
            return TokenType.RESPONSE;
        } else {
            throw new IllegalStateException( "Unsupported token matched" );
        }
    }

    protected long matchStreamToken( long... tokenValue ) throws IOException {
        Arrays.sort(tokenValue);

        Long token;
        do {
            token = this.inputStream.readLong();
            log.debug("Token received: " + token );
        } while (Arrays.binarySearch(tokenValue, token) != 0);

        return token;
    }

    @Override
    public IForkCommand readCommand() throws IOException {
        synchronized (readLock) {
            IForkCommand command;
            try {
                command = Commands.createCommand( this.inputStream.readLong() );
            } catch ( InstantiationException e ) {
                throw new IOException("Unable to construct command object");
            }

            command.setQualifier( this.inputStream.readLong() );
            command.readFrom( this.inputStream );

            return command;
        }
    }

    @Override
    public void writeCommand(IForkCommand command) throws IOException {
        synchronized (writeLock) {
            Commons.checkNotNull(command);
            waitAvailability(outputStream);
            log.info("Writing command to data stream...");
            this.outputStream.writeLong( COMMAND_BEGIN );
            this.outputStream.writeLong( command.getCommandId() );
            this.outputStream.writeLong( command.getQualifier() );
            command.writeTo( this.outputStream );
            this.outputStream.flush();
            log.info("Data stream flushed...");
        }
    }

    @Override
    public IForkCommandResponse readResponse() throws IOException {
        synchronized (readLock) {
            IForkCommandResponse response;
            try {
                response = Commands.createResponse(this.inputStream.readLong());
                log.info("Response object " + response.getClass().getCanonicalName() + " has been created...");
            } catch ( InstantiationException e ) {
                throw new IOException( "Unable to construct response object", e );
            }

            response.setQualifier(this.inputStream.readLong());
            response.readFrom(this.inputStream);

            log.info("Response data successfuly hydrated...");

            return response;
        }
    }

    @Override
    public void writeResponse(IForkCommandResponse response) throws IOException {
        synchronized (writeLock) {
            Commons.checkNotNull(response);

            log.info("Writing response to a data stream...");
            this.outputStream.writeLong( RESPONSE_BEGIN );
            this.outputStream.writeLong( response.getId() );
            this.outputStream.writeLong( response.getQualifier() );
            response.writeTo(this.outputStream);
            this.outputStream.flush();
            log.info("Flushing data stream...");
        }
    }


}
