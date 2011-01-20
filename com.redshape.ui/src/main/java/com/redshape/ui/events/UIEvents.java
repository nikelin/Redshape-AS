package com.redshape.ui.events;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 03.01.11
 * Time: 23:00
 * To change this template use File | Settings | File Templates.
 */
public class UIEvents extends EventType {

    public UIEvents( String code ) {
        super(code);
    }

    public static class Core extends UIEvents {
        public Core( String code ) {
            super(code);
        }

        public static Core Init = new Core("Core.Init");
        public static Core Repaint = new Core("Core.Repaint");
        public static Core Unload = new Core("Core.Unload");
    }

}
