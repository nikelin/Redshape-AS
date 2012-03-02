package com.redshape.renderer;

/**
 * @author nikelin
 * @package com.redshape.renderer
 * @date Mar 16, 2010
 */
public interface IRenderer<T, V> {

    public void repaint( T renderable );
    
    public V render( T renderable );

}
