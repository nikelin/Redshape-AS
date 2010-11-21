package com.redshape.servlet.views.render;

import com.redshape.servlet.views.IView;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:34 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IViewRenderer {

    public void render( IView view ) throws RenderException;

}
