package com.redshape.ui.gwt.helpers.notification;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Created with IntelliJ IDEA.
 * User: nakham
 * Date: 30.08.12
 * Time: 16:53
 * To change this template use File | Settings | File Templates.
 */
public interface IHandlerCreator {

    public <T> AsyncCallback<T> createDelegateHandler(AsyncCallback<T> delegate);

}
