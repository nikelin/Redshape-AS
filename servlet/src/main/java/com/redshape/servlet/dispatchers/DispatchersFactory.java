package com.redshape.servlet.dispatchers;

import com.redshape.servlet.dispatchers.http.IHttpDispatcher;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class DispatchersFactory implements IDispatchersFactory {
    private static IDispatchersFactory defaultInstance = new DispatchersFactory();
    private Map<Class<? extends IHttpDispatcher>, IHttpDispatcher> dispatchers = new HashMap();

    public static IDispatchersFactory getDefault() {
        return defaultInstance;
    }

    public static void setDefault( IDispatchersFactory instance ) {
        defaultInstance = instance;
    }

    public IHttpDispatcher getDispatcher( Class<? extends IHttpDispatcher> clazz ) throws InstantiationException {
        try {
            IHttpDispatcher dispatcher = this.dispatchers.get(clazz);
            if ( dispatcher == null ) {
                this.dispatchers.put( clazz, dispatcher = clazz.newInstance() );
            }

            return dispatcher;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }

}
