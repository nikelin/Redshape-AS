package com.redshape.forker;

import com.redshape.forker.commands.InitResponse;
import com.redshape.forker.commands.handling.PauseCommandHandler;
import com.redshape.forker.events.CommandRequestEvent;
import com.redshape.forker.events.CommandResponseEvent;
import com.redshape.forker.events.ExecutorStartedEvent;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.handlers.IForkCommandHandler;
import com.redshape.forker.handlers.impl.StandardForkCommandExecutor;
import com.redshape.forker.protocol.StandardProtocol;
import com.redshape.forker.protocol.processor.StandardForkProtocolProcessor;
import com.redshape.forker.protocol.queue.IProtocolQueueCreator;
import com.redshape.forker.protocol.queue.StandardProtocolQueueCreator;
import com.redshape.utils.Commons;
import com.redshape.utils.StringUtils;
import com.redshape.utils.events.IEventListener;
import org.apache.log4j.xml.DOMConfigurator;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 12:10 PM
 * To change this template use File | Settings | File Templates.
 */
public final class Forked {

    private static BufferedWriter writer;

    private static void initLogs() {
        try {
            DOMConfigurator.configure("src/test/resources/forked.log4j.xml");
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    public static void main( String[] args ) throws IOException {
        try {
            initLogs();

            Commands.register(SimpleCommandObject.SIMPLE_COMMAND_ID, SimpleCommandObject.Command.class);
            Commands.register( SimpleCommandObject.SIMPLE_COMMAND_RESP_ID, SimpleCommandObject.Response.class );

            ExecutorService service = Executors.newFixedThreadPool(2);

            log("Application " + Forked.class.getCanonicalName() + " has been started at " + new Date().getTime() + "..." );
            log("Input arguments count: " + args.length + "");
            log("Input arguments value: " + StringUtils.join(args, ":") + "" );

            DataOutputStream out = new DataOutputStream(System.out);
            DataInputStream in = new DataInputStream(System.in);

            IProtocolQueueCreator queueCreator = new StandardProtocolQueueCreator();
            StandardProtocol protocol = new StandardProtocol(in, out);
            final StandardForkProtocolProcessor processor = new StandardForkProtocolProcessor(queueCreator.createWorkQueue(),
                    queueCreator.createResultsQueue(), protocol);
            log(protocol.getClass().getCanonicalName() + " implementation selected...");

            log("Attempt to execute " + SimpleCommandObject.Command.class.getCanonicalName()
                    + "(" + SimpleCommandObject.SIMPLE_COMMAND_ID + ") command...");

            log("Command executed successfully...");

            final IForkCommandExecutor executor = new StandardForkCommandExecutor(processor,
                    Commons.<IForkCommandHandler>set(
                        new PauseCommandHandler(),
                        new SimpleCommandObject.Handler()
                    ));

            log("Server commands executor initialized...");
            executor.addEventListener(CommandRequestEvent.class, new IEventListener<CommandRequestEvent>() {
                @Override
                public void handleEvent(CommandRequestEvent event) {
                    log("New command execution request received...");
                }
            });

            executor.addEventListener(CommandResponseEvent.class, new IEventListener<CommandResponseEvent>() {
                @Override
                public void handleEvent(CommandResponseEvent event) {
                    log("Command execution response ready to be sent...");
                }
            });

            executor.addEventListener(ExecutorStartedEvent.class, new IEventListener<ExecutorStartedEvent>() {
                @Override
                public void handleEvent(ExecutorStartedEvent event) {
                    onStarted(executor);
                }
            });

            log("Executor event handlers bounded...");
            service.execute(executor);

            service.submit(processor).get();
        } catch ( Throwable e ) {
            log("Unexpected exception has been throw!");
            log( e.getMessage() );
        }
    }

    private static void onStarted( IForkCommandExecutor executor ) {
        try {
            executor.respond(new InitResponse(IForkCommandResponse.Status.SUCCESS) );
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    private static void log( String message ) {
    }

}
