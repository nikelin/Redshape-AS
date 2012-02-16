package com.redshape.utils.system;

import com.redshape.utils.system.console.IConsole;

import java.io.IOException;
import java.util.UUID;

/**
 * @author nikelin
 * @date 20:18
 */
public interface ISystemFacade {

	public void init();

	public boolean isUnderRoot() throws IOException;

	public UUID detectStationId() throws IOException;

	public IConsole getConsole();

	public void reboot() throws IOException;

	public void reboot( Integer delay ) throws IOException;

	public void shutdown() throws IOException;

	public void shutdown( Integer delay )  throws IOException;

    public Integer getStationArch() throws IOException;

}
