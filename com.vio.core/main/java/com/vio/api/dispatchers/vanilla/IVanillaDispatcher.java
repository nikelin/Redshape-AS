package com.vio.api.dispatchers.vanilla;

import com.vio.api.dispatchers.IDispatcher;
import com.vio.io.protocols.vanilla.request.InterfaceInvocation;
import com.vio.io.protocols.response.IResponse;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.server.ServerException;
import com.vio.utils.ObservableObject;

public interface IVanillaDispatcher extends ObservableObject, IDispatcher {
	
	public void dispatch( IRequester requester, InterfaceInvocation invoke, IResponse response ) throws ServerException;
	
}