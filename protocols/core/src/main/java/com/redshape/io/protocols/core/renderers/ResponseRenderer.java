package com.redshape.io.protocols.core.renderers;

import com.redshape.io.protocols.core.response.IResponse;
import com.redshape.renderer.IRenderer;
import com.redshape.renderer.RendererException;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 15, 2010
 * Time: 12:04:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResponseRenderer<V, T extends IResponse> extends IRenderer<T, V> {

    public V render( Throwable e ) throws RendererException;

    public V render( T response ) throws RendererException;

    public byte[] renderBytes( Throwable exception ) throws RendererException;

    public byte[] renderBytes( T response ) throws RendererException;

    public byte[] renderBytes( Collection<T> response ) throws RendererException;
}
