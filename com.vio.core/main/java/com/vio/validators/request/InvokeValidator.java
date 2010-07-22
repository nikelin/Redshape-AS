package com.vio.validators.request;

import com.vio.io.protocols.vanilla.request.InterfaceInvocation;

import java.util.List;

/**
 * @author nikelin
 */
public interface InvokeValidator {

    public List<String> getMessages();

    public boolean validate( InterfaceInvocation request );

}