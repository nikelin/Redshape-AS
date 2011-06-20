package com.redshape.daemon;

import com.redshape.utils.events.IEvent;

/**
 * @author nikelin
 */
public interface IDaemonEvent extends IEvent {

    public Integer getDaemonId();

    public void setDaemonId(Integer id) ;

}
