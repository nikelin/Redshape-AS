package com.redshape.utils;

import com.redshape.utils.range.IntervalRange;
import com.redshape.utils.range.ListRange;
import com.redshape.utils.range.RangeBuilder;
import com.redshape.utils.range.RangeUtils;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 9/23/11 1:43 PM
 */
public class RangeUtilsTest {

	@Test
	public void main() {
		ListRange<Integer> range = RangeBuilder.fromString("1-5,6,10-13");
		assertEquals( 3, range.getSubRanges().size() );
		assertTrue( range.inRange(4) );
		assertTrue( range.inRange(11) );
		assertTrue( RangeUtils.checkIntersections( range, RangeBuilder.createInterval(IntervalRange.Type.INCLUSIVE, 9, 11) ) );
		assertTrue( RangeUtils.checkIntersections( range, RangeBuilder.createSingular(3) ) );
	}

}
