package com.redshape.renderer;

import java.util.Collection;

/**
 * @author nikelin
 * @package com.redshape.renderer
 * @date Mar 16, 2010
 */
public interface IRenderer<T, V> {
    
    public V render( T renderable ) throws RendererException;

    public V render( Collection<T> renderable ) throws RendererException;

}
