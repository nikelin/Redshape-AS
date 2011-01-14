package com.redshape.server.policy;

import com.redshape.exceptions.ExceptionWithCode;
import com.redshape.server.IServer;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 14, 2010
 * Time: 1:46:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IPolicy<T, V extends IServer> {

    public void setContext( V server );

    public V getContext();

    public ApplicationResult applicate( T subject );

}
