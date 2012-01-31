package com.redshape.servlet.views;

import com.redshape.servlet.core.controllers.IAction;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/11/10
 * Time: 12:33 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ILayout extends IView {

    public IAction getDispatchAction();

    public void setContent( IView view );

    public IView getContent();

}
