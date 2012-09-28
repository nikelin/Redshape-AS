package com.redshape.ui.data;

/**
 * Data model events
 * 
 * @author root
 */
public class ModelEvent extends DataEvents {

	protected ModelEvent(String code) {
		super(code);
	}

	/**
	 * Raises by data models when some of their fields going to be changed 
	 */
	public static final ModelEvent CHANGED = new ModelEvent("DataEvents.ModelEvent.CHANGED");

	/**
	 * Raises by data modes to inform store about need to remove them
	 */
	public static final ModelEvent REMOVED = new ModelEvent("DataEvents.ModelEvent.REMOVED");

}
