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

package com.redshape.ui.gwt;

import com.google.gwt.dom.client.Element;
import com.redshape.utils.Commons;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.ui.gwt
 * @date 2/21/12 {2:37 PM}
 */
public final class FormUtils {

    public static String buildURI( Element element ) {
        if ( element == null ) {
            Commons.checkNotNull(element);
        }

        StringBuilder builder = new StringBuilder();

        int i = 0;
        Map<String, String> values = buildMap(element);
        for ( Map.Entry<String, String> entry : values.entrySet() ) {
            builder.append( entry.getKey() ).append("=").append( entry.getValue() );

            if ( i++ != values.size() - 1 ) {
                builder.append("&");
            }
        }

        return builder.toString();
    }

    public static Map<String, String> buildMap( Element element ) {
        Map<String, String> values = new HashMap<String, String>();
        buildMap(element, values );
        return values;
    }

    public static void buildMap( Element element, Map<String, String> values ) {
        Element child = element.getFirstChildElement();
        while ( child != null ) {
            String tagName = child.getTagName().toLowerCase();
            if ( !( tagName.equals("input")
                    || tagName.equals("textarea")
                    || tagName.equals("select") ) ) {
                if ( child.hasChildNodes() ) {
                    buildMap(child, values);
                }
            } else if ( child.getAttribute("name") != null ) {
                values.put( child.getAttribute("name"), Commons.select(child.getNodeValue(), "") );
            }

            child = child.getNextSiblingElement();
        }
    }
    
}
