package com.redshape.forker;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:43 PM}
 */
public interface IFork {

    public boolean isPaused() throws ProcessException;

    public void resume() throws ProcessException;

    public void shutdown() throws ProcessException;

    public void pause() throws ProcessException;

    public <T extends IForkCommandResponse> T submit(IForkCommand command)
            throws ProcessException;

}
