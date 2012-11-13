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

package com.redshape.renderer.json.renderers;

import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.utils.Commons;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {3:10 PM}
 */
public abstract class AbstractJSONRenderer<T> implements IRenderer<T, String> {

    private IRenderersFactory renderersFactory;

    protected AbstractJSONRenderer(IRenderersFactory renderersFactory) {
        Commons.checkNotNull(renderersFactory);
        this.renderersFactory = renderersFactory;
    }

    protected IRenderersFactory getRenderersFactory() {
        return renderersFactory;
    }

    protected String createObject( String... fields ) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");

        int i = 0;
        for ( String field : fields ) {
            i += 1;

            if ( field != null ) {
                builder.append(field);

                if ( i != fields.length ) {
                    builder.append(",");
                }
            }
        }

        builder.append("}");

        String result = builder.toString();
        if ( result.endsWith(",}") ) {
            return result.substring(0, result.length() - 2).concat("}");
        }

        return result;
    }
    
    protected String createObject( Map<String, Object> fields ) {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        
        int i = 0;
        for ( Map.Entry<String, Object> field : fields.entrySet() ) {
            if ( field.getValue() == null ) {
                continue;
            }

            builder.append(
                this.createField(field.getKey(),
                    field.getValue() == null ?
                        "null" :
                        this.renderersFactory
                            .forEntity(field.getValue())
                            .render(field.getValue())
                )
            );
            
            if ( i++ != fields.size() - 1 ) {
                builder.append(",");
            }
        }

        builder.append("}");

        return builder.toString();
    }

    protected String createList( Object... list ) {
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        
        int i = 0;
        for ( Object value : list ) {
            i++;

            if ( value != null ) {
                builder.append( String.valueOf(value));

                if ( i != list.length  ) {
                   builder.append(",");
                }
            }
        }

        builder.append("]");

        String result = builder.toString();
        if ( result.endsWith(",]") ) {
            return result.substring(0, result.length() - 2 ).concat("]");
        }

        return result;
    }

    protected String createField( String name, Object value ) {
        return "\"" + name + "\"" + ":" + String.valueOf(value);
    }

}
