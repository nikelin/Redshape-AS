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
public interface ResponseRenderer extends Renderer<IResponse> {

    public Object render( ExceptionWithCode e ) throws RendererException;

    public Object render( IResponse e ) throws RendererException;

    public byte[] renderBytes( ExceptionWithCode e ) throws RendererException;

    public byte[] renderBytes( IResponse e ) throws RendererException;

    public byte[] renderBytes( Collection<? extends IResponse> e ) throws RendererException;
}
