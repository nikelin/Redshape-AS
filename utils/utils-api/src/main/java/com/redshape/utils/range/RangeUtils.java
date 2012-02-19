package com.redshape.utils.range;

import com.redshape.utils.Commons;
import com.redshape.utils.ILambda;
import com.redshape.utils.InvocationException;
import com.redshape.utils.Lambda;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.range
 * @date 8/14/11 6:37 PM
 */
public final class RangeUtils {
    private static final String CHECK_INTERSECTIONS_METHOD = "checkIntersections";

    private static final Map<Class<?>,Map<Class<?>, Lambda<Boolean>>> checkIntersectionsMapping
            = new HashMap<Class<?>,Map<Class<?>, Lambda<Boolean>>>();

    static {
        checkIntersectionsMapping.put(
                ListRange.class,
                Commons.map(
                    Commons.<Class<?>, Lambda<Boolean>>pair(
                            ListRange.class,
                            new Lambda<Boolean>() {
                                @Override
                                public Boolean invoke(Object... object) throws InvocationException {
                                    ListRange source = (ListRange) object[0];
                                    ListRange target = (ListRange) object[1];

                                    for ( IRange sourceSubRange : (Collection<IRange>) source.getSubRanges() ) {
                                        for ( IRange targetSubRange : (Collection<IRange>) target.getSubRanges() ) {
                                            if ( checkIntersections(
                                                    sourceSubRange,
                                                    targetSubRange ) ) {
                                                return true;
                                            }
                                        }
                                    }

                                    return false;
                                }
                            }
                    ),
                    Commons.<Class<?>, Lambda<Boolean>>pair(
                            SingularRange.class,
                            new Lambda<Boolean>() {
                                @Override
                                public Boolean invoke(Object... object) throws InvocationException {
                                    ListRange<? extends Comparable> source = (ListRange<? extends Comparable>) object[0];
                                    SingularRange<? extends Comparable> target = (SingularRange<? extends Comparable>) object[1];

                                    return checkIntersections( target, source );
                                }
                            }
                    ),
                    Commons.<Class<?>, Lambda<Boolean>>pair(
                            IntervalRange.class,
                            new Lambda<Boolean>() {
                                @Override
                                public Boolean invoke(Object... object) throws InvocationException {
                                    ListRange source = (ListRange) object[0];
                                    IntervalRange target = (IntervalRange) object[1];

                                    for ( IRange subRange : (Collection<IRange>) source.getSubRanges() ) {
                                        if ( checkIntersections( subRange, target ) ) {
                                            return true;
                                        }
                                    }

                                    return false;
                                }
                            }
                    )
                )
        );
        checkIntersectionsMapping.put(
                IntervalRange.class,
                Commons.<Class<?>, Lambda<Boolean>>map(
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                SingularRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        assertArgumentsCount(object, 2);

                                        return checkIntersections(
                                            (SingularRange<? extends Comparable>) object[1],
                                            (IntervalRange<? extends Comparable >) object[0]
                                        );
                                    }
                                }
                        ),
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                ListRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        return checkIntersections( (ListRange<? extends Comparable>) object[1],
                                                (IntervalRange<? extends Comparable>) object[0] );
                                    }
                                }
                        ),
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                IntervalRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        IntervalRange source = (IntervalRange) object[0];
                                        IntervalRange target = (IntervalRange) object[1];

                                        switch (source.getType()) {
                                            case INCLUSIVE:
                                                return source.getStart().compareTo(target.getEnd()) != 1
                                                        && source.getEnd().compareTo(target.getStart()) != -1;
                                            case EXCLUSIVE:
                                                return source.getStart().compareTo(target.getEnd()) == -1
                                                        && source.getEnd().compareTo(target.getStart()) == 1;
                                            default:
                                                throw new IllegalArgumentException("Unsupported interval range type");
                                        }
                                    }
                                }
                        )
                )
        );
        checkIntersectionsMapping.put(
                SingularRange.class,
                Commons.<Class<?>, Lambda<Boolean>>map(
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                SingularRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        assertArgumentsCount(object, 2);

                                        return ( (SingularRange<?>) object[0]).getValue()
                                                .equals(((SingularRange<?>) object[1]).getValue());
                                    }
                                }
                        ),
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                IntervalRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        SingularRange source = (SingularRange) object[0];
                                        IntervalRange target = (IntervalRange) object[1];


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
                                }
                        ),
                        Commons.<Class<?>, Lambda<Boolean>>pair(
                                ListRange.class,
                                new Lambda<Boolean>() {
                                    @Override
                                    public Boolean invoke(Object... object) throws InvocationException {
                                        SingularRange source = (SingularRange) object[0];
                                        ListRange target = (ListRange) object[1];

                                        for ( IRange subRange : (Collection<IRange>) target.getSubRanges() ) {
                                            if ( checkIntersections(source, subRange) ) {
                                                return true;
                                            }
                                        }

                                        return false;
                                    }
                                }
                        )
                )
        );
    }

    public static <T extends Comparable<T>>
    boolean checkIntersections( IRange<T> source, IRange<? extends Comparable> target ) {
        if ( source.isEmpty() || target.isEmpty() ) {
            return false;
        }

        if ( null == source || null == target ) {
            throw new IllegalArgumentException("<null>");
        }

        Map<Class<?>, Lambda<Boolean>> mappings;
        if ( null == ( mappings = checkIntersectionsMapping.get(source.getClass() ) ) ) {
            throw new IllegalArgumentException("Unsupported range type "
                    + target.getClass().getCanonicalName() );
        }

        ILambda<Boolean> method;
        if ( null == ( method = mappings.get( target.getClass() ) ) ) {
            throw new IllegalArgumentException("Unsupported range type "
                    + target.getClass().getCanonicalName() );
        }

        try {
            return method.invoke(source, target);
        } catch ( Throwable e ) {
            throw new RuntimeException("Unable to invoke method ", e );
        }
    }


}
