package com.redshape.io.net.auth.impl.providers;

import com.redshape.io.net.auth.AbstractCredentialsProvider;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 12, 2010
 * Time: 1:06:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleProvider extends AbstractCredentialsProvider {

    public SimpleProvider() {
        super();
    }

    public boolean isInitialized() {
        return true;
    }
}
