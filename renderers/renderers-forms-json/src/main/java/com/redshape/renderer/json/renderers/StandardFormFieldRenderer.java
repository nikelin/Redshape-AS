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

import com.redshape.form.IFormField;
import com.redshape.renderer.IRenderersFactory;

import java.util.Collection;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.json.renderers
 * @date 3/1/12 {2:59 PM}
 */
public class StandardFormFieldRenderer extends AbstractJSONRenderer<IFormField> {

    public StandardFormFieldRenderer( IRenderersFactory renderersFactory ) {
        super(renderersFactory);
    }

    @Override
    public void repaint(IFormField renderable) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String render(IFormField renderable) {
        StringBuilder builder = new StringBuilder();
        builder.append(
                this.createObject(
                        this.createField("id", "\"" + renderable.getId() + "\""),
                        this.createField("name", "\"" + renderable.getCanonicalName() + "\""),
                        this.createField("messages", this.getRenderersFactory()
                                .forEntity(Collection.class)
                                .render(renderable.getMessages())),
                        this.createField("valid", renderable.isValid())
                )
        );

        return builder.toString();
    }
}
