package com.vio.io.protocols.http.hydrators;

import com.vio.io.protocols.hydrators.RequestHydrator;
import com.vio.io.protocols.http.request.HttpMethod;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:25:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface HttpRequestHydrator extends RequestHydrator {

    public HttpMethod readMethod();

    public String readUri();

    public String readBody();

}