package com.redshape.ui.data.bindings;

import com.redshape.ui.application.UIException;
import com.redshape.ui.data.bindings.views.IComposedModel;

/**
 * Interface for all view model builders.
 *
 * Expect some bean class object as input and based on
 * it's metadata gathered through @see com.redshape.bindings.IBeanInfo
 * generate UI objects cascade.
 *
 * This interface is a part of Redshape UI Automation package.
 *
 * @author nikelin
 */
public interface IViewModelBuilder {

    /**
     * Return true is this class type has been already rendered
     * and present in a renderer cache storage.
     *
     * @param type
     * @return
     * @throws UIException
     */
	public boolean isProcessed( Class<?> type ) throws UIException;

    /**
     * Process metadata of a given class type
     * @param type
     * @return
     * @throws UIException
     */
	public IComposedModel createUI( Class<?> type ) throws UIException;

    /**
     * Process metadata of a given class type
     * @param type
     * @param name
     * @return
     * @throws UIException
     */
	public IComposedModel createUI( Class<?> type, String name ) throws UIException;

    /**
     * Process metadata of a given class type and based on a @param processDeffered
     * value neither process or not it's children.
     *
     * @param type
     * @param processDeffered
     * @return
     * @throws UIException
     */
	public IComposedModel createUI( Class<?> type, boolean processDeffered ) throws UIException;

    /**
     * @todo: Needs to remove this method from a public interface
     *
     * @deprecated
     * @param type
     * @param id
     * @param name
     * @param processDeffered
     * @return
     * @throws UIException
     */
	public IComposedModel createUI( Class<?> type, String id, String name, boolean processDeffered ) throws UIException;
}
