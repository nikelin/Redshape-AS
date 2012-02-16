package com.redshape.utils.streams;

import com.redshape.utils.TimeSpan;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.ClosedByInterruptException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.streams
 * @date 1/31/12 {5:21 PM}
 */
public class StreamWaiter extends AbstractStreamWaiter {

    private InputStream source;
    private Priority priority;
    
    public StreamWaiter( InputStream stream ) {
        super(stream);

        this.source = stream;
    }

    @Override
    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    @Override
    public void await() throws IOException {
        if ( -1 != this.getStream().read() ) {
            this.invokeHandlers();
        }
    }

    protected void invokeHandlers() throws IOException {
        for ( IStreamEventHandler handler : this.getHandlers() ) {
            handler.onData(this.getStream());
        }
    }

    @Override
    public void awaitUntil(TimeSpan span) throws IOException, InterruptedException {
        final IOException maybeException[] = new IOException[1];
        final Thread reader = new Thread() {
            public void run() {
                try {
                    StreamWaiter.this.invokeHandlers();
                } catch (ClosedByInterruptException e) {
                    System.err.println("Reader interrupted.");
                } catch (IOException e) {
                    maybeException[0] = e;
                }
            }
        };

        Thread interruptor = new Thread() {
            public void run() {
                reader.interrupt();
            }
        };

        reader.start();
        for(;;) {
            reader.join( span.getType().toMillis( span.getValue() ) );
            if (!reader.isAlive())
                break;

            interruptor.start();
            interruptor.join( span.getType().toMillis(span.getValue()) );
            reader.join(span.getType().toMillis(span.getValue()) );

            if (!reader.isAlive())
                break;

            throw new IOException("Unable to read from stream");
        }

        if ( maybeException[0] != null ) {
            throw maybeException[0];
        }
    }
}
