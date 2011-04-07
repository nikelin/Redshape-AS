package com.redshape.ui.state;

import com.redshape.ui.events.IEventDispatcher;
import com.redshape.utils.serializing.ObjectsFlusher;
import com.redshape.utils.serializing.ObjectsLoader;

import java.util.Date;
import java.util.Map;

/**
 * UI states managing entity.
 *
 * Responsible for backup and restoring UI data and structure.
 *
 * @author root
 * @date 07/04/11
 * @package com.redshape.ui.state
 */
public interface IUIStateManager extends IEventDispatcher {

	/**
	 * Revert to N-1 version
	 * @throws StateException
	 */
	public void restore() throws StateException;

    /**
         * Backup state to persistent using applied objects flusher and increase current revision by one.
         */
    public void backup() throws StateException;

    /**
         * Revert current state to given version. If version exists loads it through specified unmarshalling manager
         * otherwise throught StateException.
         * @param rev
         */
    public void restore( Integer rev ) throws StateException;

    /**
         * Return information about current state version ( 0 - if never saved before )
         * @return
        */
    public Integer getRevision();

    /**
         * Get all revisions
         *
         * @return
         */
    public Map<Date, Integer> getRevisions();

    /**
         * Method to enable period state backup
         * @param value
         */
    public void setPeriodicEnabled( boolean value );


	/**
	 *Method to change versioning mode state ( on/off )
	 * @param value
	 */
	public void setDoVersioning( boolean value );

	/**
 	 * Indicates versioning mode state
	 * @return
	 */
	public boolean doVersioning();

    /**
         * Indicates periodic backups processing state
         * @return
         */
    public boolean isPeriodicEnabled();

    /**
         * Specify interval of periodic backups if their enabled
         * @param value
         */
    public void setPeriodicInterval( int value );

    /**
         * @return
         */
    public int getPeriodicInterval();

    /**
         * Specifies unmarshalling manager to load state from specified location
         * @param loader
         */
    public void setLoader( ObjectsLoader loader );

    /**
         * Specifies marshalling manager to save state within specified location
         * @param flusher
         */
    public void setFlusher( ObjectsFlusher flusher );

    /**
         * Change flushing location
         * @param location
         */
    public void setLocation( String location );

    public boolean isFeatureEnabled( UIStateFeature feature );

    public void enableFeature( UIStateFeature feature );

    public void disableFeature( UIStateFeature feature );

}
