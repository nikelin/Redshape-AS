package com.redshape.io.protocols.vanilla.response;

import com.redshape.io.protocols.core.response.IResponse;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:54:19 AM
 * To change this template use File | Settings | File Templates.
 */
public interface IApiResponse extends IResponse {

    public List<IApiResponse> getResponses();

    public void addResponse( IApiResponse response );

}
