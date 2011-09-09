package com.redshape.servlet.form;

import com.redshape.servlet.form.data.IFieldDataProvider;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.form
 * @date 9/9/11 3:33 PM
 */
public interface IDataFormField<T> extends IFormField<T> {

	public void setDataProvider( IFieldDataProvider<T> provider );

}
