package com.redshape.forker;

import com.redshape.forker.commands.InitResponse;
import com.redshape.forker.events.CommandRequestEvent;
import com.redshape.forker.events.CommandResponseEvent;
import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.utils.events.IEventListener;
import com.redshape.utils.tests.AbstractContextAwareTest;
import junit.framework.Assert;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 12:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class ForkingManagerTest extends AbstractContextAwareTest<Object> {

    static {
        initLogs();
    }

    private static final Logger log = Logger.getLogger(ForkingManagerTest.class);

    public ForkingManagerTest() {
        super("src/test/resources/app-config.xml");
    }

    private static void initLogs() {
        try {
            DOMConfigurator.configure("src/test/resources/manager.log4j.xml");
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    public IForkManager getForkManager() {
        return this.getContext().getBean(IForkManager.class);
    }

    @Test
    public void testMain() throws Exception {
        Commands.register( SimpleCommandObject.SIMPLE_COMMAND_ID, SimpleCommandObject.Command.class );
        Commands.register( SimpleCommandObject.SIMPLE_COMMAND_RESP_ID, SimpleCommandObject.Response.class );

        final IForkManager manager = this.getForkManager();
        manager.addClassPath( Logger.class.getProtectionDomain().getCodeSource().getLocation().toExternalForm() );
        manager.enableDebugMode(false); // enable to run in JDWP-mode
        manager.setDebugPort(5999);
        manager.setDebugHost("localhost");

        try {
            manager.setMemoryInitial(128);
            manager.setMemoryLimit(128);

            IFork fork = manager.acquireClient(Forked.class);
            Assert.assertFalse( manager.getClientsList().isEmpty() );
            Assert.assertNotNull(fork.getPID());

            final IForkCommandExecutor executor = manager.getCommandsExecutor(fork, false);
            executor.addEventListener(CommandRequestEvent.class, new IEventListener<CommandRequestEvent>() {
                @Override
                public void handleEvent(CommandRequestEvent event) {
                log.info("Request Event: " + event.getCommand().toString() );
                }
            });

            executor.addEventListener(CommandResponseEvent.class, new IEventListener<CommandResponseEvent>() {
                @Override
                public void handleEvent(CommandResponseEvent event) {
                log.info("Response Event: " + event.getResponse().toString() );
                if ( event.getResponse() instanceof InitResponse ) {
                    onExecutorStarted(executor);
                } else if ( event.getResponse() instanceof SimpleCommandObject.Response ) {
                    try {
                        manager.shutdownAll();
                        executor.stop();
                    } catch ( Throwable e ) {
                        log.error(e.getMessage(), e);
                        System.exit(0);
                    }
                }
                }
            });

            Thread executorThread = new Thread(executor);
            executorThread.setName("Executor Thread");
            executorThread.start();
            executorThread.join();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }finally {
            manager.shutdownAll();
        }
    }

    private static void onExecutorStarted( final IForkCommandExecutor executor ) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(executor.isStarted()) {
                        executor.executeAsync(new SimpleCommandObject.Command());
                        Thread.sleep(100);
                    }
                } catch ( ProcessException e ) {
                    Assert.fail( e.getMessage() );
                } catch ( InterruptedException e ) {

                }
            }
        });

        thread.setName("Executor Sampler Thread");

        thread.start();

        try {
            thread.join();
        } catch ( Throwable e ) {
        }
    }
}
