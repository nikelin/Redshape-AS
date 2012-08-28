package com.redshape.forker;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IFork {

    public DataInputStream getInput();

    public DataOutputStream getOutput();

    public int getPID();

    public boolean isPaused() throws ProcessException;

    public void resume() throws ProcessException;

    public void shutdown() throws ProcessException;

    public void pause() throws ProcessException;

}
