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

package com.redshape.net.ssh.connection;

import com.redshape.net.capability.CapabilityType;
import com.redshape.net.connection.ConnectionException;
import com.redshape.net.connection.IServerConnection;
import com.redshape.net.connection.capabilities.IConsoleCapabilitySupport;
import com.redshape.net.connection.capabilities.IFilesystemCapabilitySupport;
import com.redshape.net.connection.capabilities.IServerCapabilitySupport;
import com.redshape.net.connection.capabilities.IServerCapabilitySupportFactory;
import com.redshape.net.ssh.connection.capabilities.ConsoleCapabilitySupport;
import com.redshape.net.ssh.connection.capabilities.FilesystemCapabilitySupport;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 3/20/12
 * Time: 7:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class SshCapabilitySupportFactory implements IServerCapabilitySupportFactory {
    private Map<CapabilityType, Class<? extends IServerCapabilitySupport>> registry
            = new HashMap<CapabilityType, Class<? extends IServerCapabilitySupport>>();

    @Override
    public IFilesystemCapabilitySupport getFilesystemCapability(IServerConnection connection) {
        return new FilesystemCapabilitySupport( (SshConnectionSupport) connection);
    }

    @Override
    public IConsoleCapabilitySupport getConsoleCapability(IServerConnection connection) {
        return new ConsoleCapabilitySupport( (SshConnectionSupport) connection );
    }

    @Override
    public void registerSupport(CapabilityType type, Class<? extends IServerCapabilitySupport> support) {
        this.registry.put( type, support );
    }

    @Override
    public <T extends IServerCapabilitySupport> T getCapability(CapabilityType capability, IServerConnection connection) throws ConnectionException {
        try {
            return (T) this.registry.get(capability).getConstructor(connection.getClass())
                .newInstance(connection);
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }
}
