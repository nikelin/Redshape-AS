package com.redshape.daemon.events;

import com.redshape.utils.events.AbstractEvent;
import com.redshape.daemon.IDaemonEvent;
import com.redshape.daemon.IRemoteService;

public class ServiceBindedEvent extends AbstractEvent implements IDaemonEvent {
	private static final long serialVersionUID = 8991847611607711518L;
	
	private IRemoteService service;
	private Integer daemonId;
	
	public ServiceBindedEvent( IRemoteService service ) {
		this.service = service;
	}
	
	public <T extends IRemoteService> T getService() {
		return (T) this.service;
	}

	public void setDaemonId( Integer id ) {
		this.daemonId = id;
	}

	public Integer getDaemonId() {
		return this.daemonId;
	}
	
}
