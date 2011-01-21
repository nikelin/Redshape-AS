package com.redshape.validators.request;

import com.redshape.io.protocols.vanilla.request.IApiRequest;

import java.util.List;

/**
 * @author nikelin
 */
public interface InvokeValidator {

    public List<String> getMessages();

    public boolean validate( IApiRequest request );

}