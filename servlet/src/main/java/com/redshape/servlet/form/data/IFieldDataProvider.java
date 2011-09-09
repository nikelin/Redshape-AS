package com.redshape.servlet.form.data;

import java.util.Map;

/**
 * Provides ability to implement invalidatable data fields
 * which will be able to synchronize self state with some
 * persistent data source or elsewhere.
 *
 * Can be used as anonymous interface to provide in each
 * field instance.
 *
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.data
 * @date 9/9/11 3:37 PM
 */
public interface IFieldDataProvider<T> {

	/**
	 * Invalidate current provider state to check
	 * that data is in actual state.
	 *
	 * @return
	 */
	public boolean invalidate();

	/**
	 * Provide collection of values
	 *
	 * @return
	 */
	public Map<String, T> provideCollection();

	/**
	 * Provide single value
	 *
	 * @return
	 */
	public T provide();

}
