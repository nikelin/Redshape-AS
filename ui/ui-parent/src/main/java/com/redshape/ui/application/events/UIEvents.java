package com.redshape.ui.application.events;

/**
 * @author nikelin
 */
public class UIEvents extends EventType {

    protected UIEvents( String code ) {
        super(code);
    }

    public static class Core extends UIEvents {
        protected Core( String code ) {
            super(code);
        }

		public static class Refresh extends Core {

			protected Refresh( String code ) {
				super(code);
			}

			public static final Refresh Settings = new Refresh("Core.Refresh.Settings");
			public static final Refresh Stores = new Refresh("Core.Refresh.Stores");
		}

        public static Core Exit = new Core("Core.Exit");
        public static Core Error = new Core("Core.Error");
        public static Core Init = new Core("Core.Init");
        public static Core Repaint = new Core("Core.Repaint");
        public static Core System = new Core("Core.System");
        public static Core Unload = new Core("Core.Unload");
    }

}
