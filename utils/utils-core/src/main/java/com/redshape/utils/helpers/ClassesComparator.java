package com.redshape.utils.helpers;

import java.util.Comparator;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils.helpers
 * @date 2/17/12 {2:24 PM}
 */
public class ClassesComparator<T> implements Comparator<Class<? extends T>> {

    @Override
    public int compare(Class<? extends T> source, Class<? extends T> target) {
        if ( source.isInterface() ) {
            return target.isAssignableFrom(source) ? -1 : ( target.equals(source) ? 0 : 1);
        } else {
            return target.isInstance(source) ? -1 : ( target.equals(source) ? 0 : 1 );
        }
    }
}
