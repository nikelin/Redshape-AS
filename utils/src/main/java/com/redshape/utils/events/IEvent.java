package com.redshape.utils.events;

import java.io.Serializable;

/**
 * @author nikelin
 */
public interface IEvent extends Serializable {

    public <V> V[] getArgs();

    public void setArgs(Object... args);

    public <V> V getArg(int index);

}
