package com.redshape.comet.server;

import com.redshape.comet.ClientsManager;
import org.eclipse.jetty.websocket.WebSocket;

import java.util.Date;

public class WebSocketWrapper implements WebSocket.OnTextMessage {
    private Connection _connection;
    private Date created;

    public WebSocketWrapper() {
        this.created = new Date();
    }

    public Date getCreated() {
        return created;
    }

    public void onOpen(Connection connection) {
        _connection=connection;
        ClientsManager.registerConnection(this);
    }

    public void onClose(int closeCode, String message) {
        ClientsManager.removeConnection(this);
    }

    public Connection getConnection() {
        return this._connection;
    }

    public void onMessage(String data) {

    }
}