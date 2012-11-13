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

package com.redshape.renderer.forms.renderers;

import com.google.gwt.user.client.Timer;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.IRenderersFactory;
import com.redshape.renderer.managers.RenderersManager;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.renderer.forms.renderers
 * @date 2/22/12 {2:18 AM}
 */
public class InvalidationThread extends Timer {
    private IRenderersFactory renderersFactory;

    public InvalidationThread( IRenderersFactory renderersFactory ) {
        this.renderersFactory = renderersFactory;
    }

    protected IRenderersFactory getRenderersFactory() {
        return this.renderersFactory;
    }

    @Override
    public void run() {
        for ( Object object : RenderersManager.getInstance().getInvalid() ) {
            IRenderer<Object, ?> renderer = this.getRenderersFactory().forEntity(object);
            if ( renderer == null ) {
                RenderersManager.getInstance().markValid(object);
                continue;
            }

            renderer.repaint(object);
        }
    }

}
