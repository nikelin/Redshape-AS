package com.redshape.forker;

import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IForkManager {

    public void addClassPath( String path );

    public void addClassPath( String[] path );

    public IFork acquireClient( Class<?> client, String[] args ) throws ProcessException;

    public IFork acquireClient( Class<?> client ) throws ProcessException;

    public List<IFork> getClientsList();

    public void shutdownAll() throws ProcessException;

    public void pauseAll() throws ProcessException;
    
}
