package com.redshape.comet;

import com.redshape.comet.server.WebSocketWrapper;
import com.redshape.forker.IForkManager;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 11/6/12
 * Time: 3:13 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ClientsManager {

    private static final Set<WebSocketWrapper> connections = new CopyOnWriteArraySet<WebSocketWrapper>();
    private static final ExecutorService executors = Executors.newFixedThreadPool(100);

    public static void registerConnection( WebSocketWrapper connection ) {
        connections.add(connection);
    }

    public static void removeConnection( WebSocketWrapper connection ) {
        connections.remove(connection);
    }

    public static Collection<WebSocketWrapper> getConnections() {
        return connections;
    }

}
