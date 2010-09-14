package com.redshape.io.protocols.vanilla.response;

import com.redshape.io.protocols.core.response.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 6, 2010
 * Time: 2:57:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class ApiResponse extends Response implements IApiResponse {
    private List<IApiResponse> responses = new ArrayList<IApiResponse>();

    public ApiResponse() { super(); }

    public ApiResponse( String id ) { super(id); }

    public List<IApiResponse> getResponses() {
        return this.responses;
    }

    public void addResponse( IApiResponse response ) {
        this.responses.add(response);
    }

}
