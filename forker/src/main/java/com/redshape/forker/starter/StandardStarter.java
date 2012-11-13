/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.forker.starter;

import com.redshape.forker.ForkClassLoader;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;
import com.redshape.forker.commands.InitResponse;
import com.redshape.forker.commands.handling.PauseCommandHandler;
import com.redshape.forker.events.CommandRequestEvent;
import com.redshape.forker.events.CommandResponseEvent;
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

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 4/24/12
 * Time: 5:04 PM
 * To change this template use File | Settings | File Templates.
 */
public final class StandardStarter extends ForkClassLoader {

    private File log;

    public StandardStarter(IForkCommandExecutor executor) {
        super(executor);

    }

    public void run() throws IOException {
        try {
            this.executor.start();
        } catch ( ProcessException e ) {
            throw new IOException( e.getMessage(), e );
        }
    }

    private static void log( String message ) {
        try {
            System.err.write( message.getBytes() );
            System.err.write( "\n".getBytes() );
        } catch ( IOException e ) {
            return;
        }
    }

    public static void main( String[] args ) {
        try {
            log("Application " + StandardStarter.class.getCanonicalName() + " has been started at " + new Date().getTime() + "..." );
            log("Input arguments count: " + args.length + "");
            log("Input arguments value: " + StringUtils.join(args, ":") + "" );

            DataOutputStream out = new DataOutputStream(System.out);
            DataInputStream in = new DataInputStream(System.in);

            IProtocolQueueCreator creator = new StandardProtocolQueueCreator();

            StandardProtocol protocol = new StandardProtocol(in, out);
            log( protocol.getClass().getCanonicalName() + " implementation selected...");

            StandardForkProtocolProcessor processor = new StandardForkProtocolProcessor(
                    creator.createWorkQueue(), creator.createResultsQueue(), protocol);

            final IForkCommandExecutor executor = new StandardForkCommandExecutor(processor,
                    Commons.<IForkCommandHandler>set(
                            new PauseCommandHandler()
                    ));

            ForkClassLoader loader = new ForkClassLoader(executor);
            log("Forked classloader initialized...");
            Thread.currentThread().setContextClassLoader(loader);

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

            log("Executor event handlers bounded...");
            Thread workerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        executor.start();
                    } catch ( ProcessException e ) {
                        log(e.getMessage());
                        throw new IllegalStateException( e.getMessage(), e );
                    }
                }
            });

            executor.respond( new InitResponse(IForkCommandResponse.Status.SUCCESS) );

            workerThread.start();
            workerThread.join();
        } catch ( Throwable e ) {
            log("Unexpected exception has been throw!");
            log(e.getMessage());
        }
    }

}
