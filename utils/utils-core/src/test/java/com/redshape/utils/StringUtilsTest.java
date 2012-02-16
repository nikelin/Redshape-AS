package com.redshape.utils;

import org.junit.Test;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 9/7/11 5:06 PM
 */
public class StringUtilsTest {

	@Test
	public void testEscape() {
		assertEquals(
			StringUtils.escape( "afla&&afla&&afla", new String[] { "&&" }, "\\" ),
			"afla\\&&afla\\&&afla"
		);
		assertEquals(
			StringUtils.escape( "afla&&afla:afla", new String[] { "&&", ":" }, "\\" ),
			"afla\\&&afla\\:afla"
		);
	}

}
