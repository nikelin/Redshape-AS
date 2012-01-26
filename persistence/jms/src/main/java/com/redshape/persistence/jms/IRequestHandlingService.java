package com.redshape.persistence.jms;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {5:37 PM}
 */
public interface IRequestHandlingService extends Runnable {

    public boolean isRunning();

    public void stop();

}
