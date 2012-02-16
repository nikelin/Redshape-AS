package com.redshape.utils.range.normalizers;

import com.redshape.utils.Function;

import java.lang.reflect.InvocationTargetException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range.normalizers
 * @date 9/28/11 2:03 PM
 */
public class NumericNormalizer extends Function<Object, Integer> {

	@Override
	public Integer invoke(Object... arguments) throws InvocationTargetException {
		if ( arguments == null || arguments.length == 0 ) {
			return null;
		}

		String argument = (String) arguments[0];
		if ( argument == null ) {
			return null;
		}

		return Integer.valueOf( argument );
	}
}
