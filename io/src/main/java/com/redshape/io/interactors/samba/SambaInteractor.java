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

package com.redshape.io.interactors.samba;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.INetworkInteractor;
import com.redshape.io.interactors.SambaConnection;
import com.redshape.utils.system.scripts.IScriptExecutor;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.io.interactors.samba
 * @date 10/22/11 9:30 PM
 */
public class SambaInteractor implements INetworkInteractor {
	private SambaConnection connection;

	public SambaInteractor( SambaConnection connection ) {
		this.connection = connection;
	}

	@Override
	/**
	 * @FIXME
	 */
	public IFilesystemNode createFile(String path) throws IOException {
		return null;
	}

	@Override
	public void execute(IScriptExecutor executor) throws IOException {
		throw new UnsupportedOperationException("Needs to be implemented");
	}

	@Override
	public IFilesystemNode getRoot() throws IOException {
		return this.connection.getFile("/");
	}

	@Override
	public IFilesystemNode getFile(String name) throws IOException {
		return this.connection.getFile(name);
	}
}
