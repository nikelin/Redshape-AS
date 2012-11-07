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