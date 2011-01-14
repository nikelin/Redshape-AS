package com.redshape.render;

import java.util.Collection;

/**
 * WebCam Project
 *
 * @author nikelin
 * @project vio
 * @package com.vio.render
 * @date Mar 16, 2010
 */
public interface IRenderer<T extends IRenderable> {
    
    public Object render( T renderable ) throws RendererException;

    public Object render( Collection<? extends T> renderable ) throws RendererException;

}
