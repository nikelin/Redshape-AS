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

package com.redshape.forker;

import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.protocol.processor.IForkProtocolProcessor;

import java.util.List;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IForkManager {

    public void setJVMArgument( String name, String value );

    public String getJVMArgument( String name );

    public Map<String, String> getJVMArguments();

    public void setJVMArguments( Map<String, String> arguments );

    public IForkCommandExecutor getCommandsExecutor( IFork fork );

    public IForkCommandExecutor getCommandsExecutor( IFork fork, boolean forceStart );

    public IForkProtocolProcessor getForkProcessor( IFork fork );

    public void setDebugHost( String value );

    public void setDebugPort( int port );

    public void enableDebugMode( boolean value );

    public void addClassPath( String path );

    public void addClassPath( String[] path );

    public IFork acquireClient( Class<?> client, String[] args ) throws ProcessException;

    public IFork acquireClient( Class<?> client ) throws ProcessException;

    public List<IFork> getClientsList();

    public void shutdownAll() throws ProcessException;

    public void pauseAll() throws ProcessException;

    public void setMemoryLimit( int value );

    public int getMemoryLimit();

    public void setMemoryInitial( int value );

    public int getMemoryInitial();

}
