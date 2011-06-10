package com.redshape.renderer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 29, 2010
 * Time: 1:27:29 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IRenderersFactory {

	/**
     * String which represent factory ID in configuration
     *
     * @return Strng
     */
    String getFactoryId();

    /**
     * Get renderer for specified class entity
     * @param clazz
     * @param <T>
     * @return
     * @throws RendererException
     */
    <T extends IRenderer> T getRenderer( Class<T> clazz ) throws RendererException;

    /**
     * Bind renderer entity to specified renderable entity
     * @param rendererClazz
     * @param renderer
     */
    void addRenderer( Class<? extends IRenderer> rendererClazz, IRenderer renderer );

    IRenderer forEntity( Object object ) throws RendererException;

    IRenderer forEntity( Class<?> object ) throws RendererException;
}
