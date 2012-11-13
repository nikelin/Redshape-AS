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

package com.redshape.ui.data.state;

import com.redshape.ui.application.events.EventType;

/**
 * UI stater manager events
 *
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public class UIStateEvent extends EventType {

    protected UIStateEvent( String code ) {
        super(code);
    }

	public static class Result extends UIStateEvent {

		protected Result( String code ) {
			super(code);
		}

		/**
		 *	Raises after successful backup operation
		 */
		public static final Result Saved = new Result("UIStateEvent.Result.Saved");

		/**
		 *	Raises after successful restore operation
		 */
		public static final Result Restored = new Result("UIStateEvent.Result.Restored");
	}

    /**
         *  Indicates need in state flushing on persistent storage
         */
    public static final UIStateEvent Save = new UIStateEvent("UIStateEvent.Save");

    /**
         * Indicated need in state reverting to given revision
         */
    public static final UIStateEvent Restore = new UIStateEvent("UIStateEvent.Restore");

}
