package com.redshape.ui.application;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 23.05.11
 * Time: 0:52
 * To change this template use File | Settings | File Templates.
 */
public interface IApplication {
    void exit();

    void start() throws ApplicationException;
}
