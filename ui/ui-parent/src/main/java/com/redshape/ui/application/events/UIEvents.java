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
