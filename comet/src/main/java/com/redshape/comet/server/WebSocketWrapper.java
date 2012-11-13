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