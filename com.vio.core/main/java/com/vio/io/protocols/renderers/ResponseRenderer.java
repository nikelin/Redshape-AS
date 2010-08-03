package com.vio.io.protocols.renderers;

import com.vio.io.protocols.response.IResponse;
import com.vio.exceptions.ExceptionWithCode;
import com.vio.render.RendererException;
import com.vio.render.Renderer;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 15, 2010
 * Time: 12:04:41 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ResponseRenderer<T extends IResponse> extends Renderer<T> {

    public Object render( ExceptionWithCode e ) throws RendererException;

    public Object render( T response ) throws RendererException;

    public byte[] renderBytes( ExceptionWithCode exception ) throws RendererException;

    public byte[] renderBytes( T response ) throws RendererException;

    public byte[] renderBytes( Collection<? extends T> response ) throws RendererException;
}
