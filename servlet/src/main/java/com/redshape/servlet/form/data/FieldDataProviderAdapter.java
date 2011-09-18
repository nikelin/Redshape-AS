package com.redshape.servlet.form.data;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form.data
 * @date 9/17/11 1:04 AM
 */
public abstract class FieldDataProviderAdapter<T> implements IFieldDataProvider<T> {

	@Override
	public boolean invalidate() {
		return false;
	}

	@Override
	public T provide() {
		return null;
	}
}
