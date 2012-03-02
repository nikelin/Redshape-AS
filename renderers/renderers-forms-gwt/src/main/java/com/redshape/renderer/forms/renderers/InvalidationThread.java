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
