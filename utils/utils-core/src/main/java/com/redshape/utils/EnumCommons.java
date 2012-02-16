package com.redshape.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 2/13/12 {1:11 PM}
 */
public final class EnumCommons {


    public static Map<String, Integer> map( Class<? extends Enum> enumClazz ) {
        try {
            Map<String, Integer> result = new HashMap<String, Integer>();
            Enum[] enumValues = (Enum[]) enumClazz.getMethod("values").invoke(null);
            for ( Enum enumValue : enumValues ) {
                result.put( enumValue.name(), enumValue.ordinal() );
            }

            return result;
        } catch ( Throwable e ) {
            return null;
        }
    }


}
