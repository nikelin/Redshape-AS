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
     * Bind renderer entity to specified renderable entity
     * @param entityClazz
     * @param renderer
     */
    public <T, V> void addRenderer( Class<T> entityClazz, Class<? extends IRenderer<T, V>> renderer );

    /**
     *
     * @param object
     * @param <T>
     * @param <V>
     * @return
     * @throws RendererException
     */
    public <T, V> IRenderer<T, V> forEntity( Class<T> object );

    public <T, V> IRenderer<T, V> forEntity( T entity );
}
