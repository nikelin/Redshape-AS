package com.redshape.io.protocols.http.hydrators;

import com.redshape.io.protocols.core.hydrators.RequestHydrator;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:25:41 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IHttpRequestHydrator extends RequestHydrator {

    public String readMethod();

    public String readUri();

    public String readProtocolVersion();

    public Map<String, String> readParams();

    public String readBody();

}