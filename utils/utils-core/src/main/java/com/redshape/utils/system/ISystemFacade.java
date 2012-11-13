/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
