package com.redshape.ui.views;

import com.redshape.ui.utils.UIConstants;

/**
 * Views managing entity
 * 
 * @author nikelin
 */
public interface IViewsManager {
	
	/**
	 * Activate view associated with specified id
	 * @param id
	 */
	public void activate( Object id ) throws ViewException;
	
	/**
	 * Unload current active view
	 */
	public void deactivate() throws ViewException;
	
	/**
	 * Check that specified ID has associated view
	 * in the registry.
	 * @param id
	 * @return
	 */
	public boolean isRegistered( Object id );
	
	/**
	 * Add view into registry with specified identifier key
	 * @param view
	 * @param id
	 */
	public void register( IView view, Object id );
	
	/**
	 * Unload view related to specified ID and drops 
	 * all information about it from registry.
	 * 
	 * @param id
	 */
	public void unregister( Object id );
	
	/**
	 * Return ID of current active view instance
	 * @return
	 */
	public Object getActive();
	
	/**
	 * Change manager output component
	 * @param area
	 */
	public void setViewArea( UIConstants.Area area );

}
