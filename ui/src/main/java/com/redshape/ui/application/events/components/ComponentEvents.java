package com.redshape.ui.application.events.components;

import com.redshape.ui.application.events.UIEvents;

public class ComponentEvents extends UIEvents {

	protected ComponentEvents( String code ) {
		super(code);
	}
	
	public static ComponentEvents ActionPerformed = new ComponentEvents("ComponentEvents.ActionPerformed");
	
	public static ComponentEvents Activated = new ComponentEvents("ComponentEvents.Activated");
	
}
