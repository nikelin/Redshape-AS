package com.redshape.forker;

import com.redshape.forker.handlers.IForkCommandExecutor;
import com.redshape.forker.protocol.processor.IForkProtocolProcessor;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IForkManager {

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
