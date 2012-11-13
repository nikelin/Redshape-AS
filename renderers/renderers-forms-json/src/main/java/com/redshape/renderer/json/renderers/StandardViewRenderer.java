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

import com.redshape.renderer.IRenderersFactory;
import com.redshape.servlet.views.IView;
import com.redshape.utils.Commons;

import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {3:32 PM}
 */
public class StandardViewRenderer extends AbstractJSONRenderer<IView> {

    public StandardViewRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public void repaint(IView renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    protected String[] renderAttributes( Map<String, Object> attributes ) {
        String[] result = new String[attributes.size()];
        
        int i = 0 ;
        for ( Map.Entry<String, Object> attribute : attributes.entrySet() ) {
            result[i++] =
                this.createField(attribute.getKey(),
                    attribute.getValue() != null ?
                       Commons.select(this.getRenderersFactory()
                               .<Object, String>forEntity(attribute.getValue())
                               .render(attribute.getValue()),
                       "")
                        : "null"
                );
        }

        return result;
    }
    
    @Override
    public String render(IView renderable) {
        StringBuilder builder = new StringBuilder();

        builder.append(
            this.createObject( this.renderAttributes(renderable.getAttributes()) )
        );
        
        return builder.toString();
    }
}
