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
