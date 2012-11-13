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

package com.redshape.comet.servlet;

import com.redshape.comet.server.WebSocketWrapper;
import org.eclipse.jetty.websocket.WebSocketFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CometServlet extends HttpServlet {
    private WebSocketFactory _wsFactory;

    public CometServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException
    {
        if (_wsFactory.acceptWebSocket(request,response))
            return;
        response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
                "WebSocket only");
    }

    @Override
    public void init() throws ServletException
    {
        // Create and configure WS factory
        _wsFactory=new WebSocketFactory(new WebSocketFactory.Acceptor()
        {
            public boolean checkOrigin(HttpServletRequest request, String origin)
            {
                // Allow all origins
                return true;
            }

            public org.eclipse.jetty.websocket.WebSocket doWebSocketConnect(HttpServletRequest request, String protocol)
            {
                if ("comet".equals(protocol))
                    return new WebSocketWrapper();
                return null;
            }
        });

        _wsFactory.setBufferSize(4096);
        _wsFactory.setMaxIdleTime(60000);
    }
}