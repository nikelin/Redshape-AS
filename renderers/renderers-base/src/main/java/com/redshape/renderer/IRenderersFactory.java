package com.redshape.renderer;

/**
 * @author nikelin
 * @package com.redshape.renderer
 */
public interface IRenderersFactory {

	/**
     * String which represent factory ID in configuration
     *
     * @return Strng
     */
    public String getFactoryId();

    /**
     * Get renderer for specified class entity
     * @param clazz
     * @param <T>
     * @return
     * @throws RendererException
     */
    public <T, V> IRenderer<T, V> getRenderer( Class<? extends IRenderer<T, V>> clazz ) 
    		throws RendererException;

    /**
     * Bind renderer entity to specified renderable entity
     * @param rendererClazz
     * @param renderer
     */
    public <T, V> void addRenderer( Class<? extends IRenderer<T, V>> rendererClazz, 
    						 IRenderer<T, V> renderer );

    public <T, V> IRenderer<T, V> forEntity( Object object ) throws RendererException;

    public <T, V> IRenderer<T, V> forEntity( Class<T> object ) throws RendererException;
}
