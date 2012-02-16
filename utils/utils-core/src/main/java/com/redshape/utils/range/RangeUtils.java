package com.redshape.utils.range;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:37 PM
 */
public final class RangeUtils {
	private static final String CHECK_INTERSECTIONS_METHOD = "checkIntersections";

	private static final Map<Class<? extends IRange<?>>,
				Map<Class<? extends IRange<?>>, Method>> checkIntersectionsMapping
				= new HashMap<Class<? extends IRange<?>>,
						Map<Class<? extends IRange<?>>, Method>>();

	static {
		for ( Method method : RangeUtils.class.getDeclaredMethods() ) {
			Class[] paramTypes = method.getParameterTypes();
			if ( method.getName().equals( CHECK_INTERSECTIONS_METHOD ) ) {
				Map<Class<? extends IRange<?>>, Method> mappings = checkIntersectionsMapping.get( paramTypes[0] );
				if ( mappings == null ) {
					checkIntersectionsMapping.put( paramTypes[0], mappings = new HashMap<Class<? extends IRange<?>>, Method>() );
				}

				mappings.put( paramTypes[1], method ) ;
			}
		}
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( IRange<T> source, IRange<T> target ) {
		if ( source.isEmpty() || target.isEmpty() ) {
			return false;
		}

		if ( null == source || null == target ) {
			throw new IllegalArgumentException("<null>");
		}

		Map<Class<? extends IRange<?>>, Method> mappings;
		if ( null == ( mappings = checkIntersectionsMapping.get(source.getClass() ) ) ) {
			throw new IllegalArgumentException("Unsupported range type "
					+ target.getClass().getCanonicalName() );
		}

		Method method;
		if ( null == ( method = mappings.get( target.getClass() ) ) ) {
			throw new IllegalArgumentException("Unsupported range type "
					+ target.getClass().getCanonicalName() );
		}

		try {
			return (Boolean) method.invoke(null, source, target);
		} catch ( Throwable e ) {
			throw new RuntimeException("Unable to invoke method ", e );
		}
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( SingularRange<T> source, SingularRange<T> target ) {
		return source.getValue().equals( target.getValue() );
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( SingularRange<T> source, IntervalRange<T> target ) {
   		switch ( target.getType() ) {
		   case EXCLUSIVE:
			   return source.getValue().compareTo( target.getStart() ) == 1
					  && source.getValue().compareTo( target.getEnd() ) == -1;
		   case INCLUSIVE:
			   return source.getValue().compareTo( target.getStart() ) != -1
					   && source.getValue().compareTo( target.getEnd() ) != 1;
		   default:
			   throw new IllegalArgumentException("Unsupported interval range type");
	   }
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( IntervalRange<T> source, SingularRange<T> target ) {
		return checkIntersections( target, source );
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( IntervalRange<T> source, IntervalRange<T> target ) {
		switch ( source.getType() ) {
			case INCLUSIVE:
				return source.getStart().compareTo( target.getEnd() ) != 1
						&& source.getEnd().compareTo( target.getStart() ) != -1;
			case EXCLUSIVE:
				return source.getStart().compareTo( target.getEnd() ) == -1
						&& source.getEnd().compareTo( target.getStart() ) == 1;
			default:
				throw new IllegalArgumentException("Unsupported interval range type");
		}
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( SingularRange<T> source, ListRange<T> target ) {
		for ( IRange<T> subRange : target.getSubRanges() ) {
			if ( checkIntersections(source, subRange) ) {
				return true;
			}
		}

		return false;
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( ListRange<T> source, ListRange<T> target ) {
		for ( IRange<T> sourceSubRange : source.getSubRanges() ) {
			for ( IRange<T> targetSubRange : target.getSubRanges() ) {
				if ( checkIntersections( sourceSubRange, targetSubRange ) ) {
					return true;
				}
			}
		}

		return false;
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( ListRange<T> source, IntervalRange<T> target ) {
		for ( IRange<T> subRange : source.getSubRanges() ) {
			if ( checkIntersections( subRange, target ) ) {
				return true;
			}
		}

		return false;
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( IntervalRange<T> source, ListRange<T> target ) {
		return checkIntersections( target, source );
	}

	public static <T extends Comparable<T>>
		boolean checkIntersections( ListRange<T> source, SingularRange<T> target ) {
		return checkIntersections( target, source );
	}


}
