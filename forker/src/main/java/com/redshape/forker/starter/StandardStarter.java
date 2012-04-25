package com.redshape.forker.starter;

import com.redshape.forker.Commands;
import com.redshape.forker.ForkClassLoader;
import com.redshape.forker.IForkCommand;
import com.redshape.forker.protocol.IForkProtocol;
import com.redshape.forker.protocol.StandardProtocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/24/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public final class StandardStarter extends ForkClassLoader {

    public StandardStarter(IForkProtocol protocol, DataInputStream input, DataOutputStream output) {
        super(protocol, input, output);
    }

    private static IForkProtocol createProtocol( String className ) {
        IForkProtocol result = null;
        try {
            Class<?> clazz = Class.forName(className);
            if ( clazz.isInstance(IForkProtocol.class) ) {
                result = (IForkProtocol) clazz.newInstance();
            }
        } catch ( Throwable e ) {}

        return result;
    }


    public void run() throws IOException {
        while ( true ) {
            IForkCommand command = this.getProtocol().readCommand( this.getInput() );
            if ( command == null ) {
                continue;
            }

            this.processCommand(command);
        }
    }

    protected void processCommand( IForkCommand command ) {
        switch ( command.getCommandId().intValue() ) {
            case (int) Commands.SHUTDOWN:
                System.exit(1);
        }
    }


    public static void main( String[] args ) {
        IForkProtocol protocol = null;
        if ( args.length > 0 ) {
            protocol = createProtocol(args[0]);
        }

        if ( protocol == null ) {
            protocol = new StandardProtocol();
        }

        StandardStarter starter = new StandardStarter(
                protocol,
                new DataInputStream(System.in),
                new DataOutputStream(System.out)
        );

        System.setIn(new ByteArrayInputStream(new byte[0]));
        System.setOut(System.err);

        Thread.currentThread().setContextClassLoader(starter);

        try {
            starter.run();
        } catch ( IOException e ) {
            System.exit(1);
        }
    }

}
