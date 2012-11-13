/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.utils;

import java.util.HashMap;
import java.util.Map;
import com.redshape.utils.Commons;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.utils
 * @date 2/13/12 {1:11 PM}
 */
public final class EnumCommons {

    public static Map<String, String> map( Class<? extends IEnum> enumClazz ) {
        try {
            Map<String, String> result = new HashMap<String, String>();
            IEnum[] enumValues = (IEnum[]) enumClazz.getMethod("values").invoke(null);
            for ( IEnum enumValue : enumValues ) {
                result.put( enumValue.toString(), enumValue.name() );
            }

            return result;
        } catch ( Throwable e ) {
            return null;
        }
    }


}
