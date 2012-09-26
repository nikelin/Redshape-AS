package com.redshape.forker.handlers;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.forker.ProcessException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IForkCommandHandler {

    public boolean isSupported( IForkCommand command );

    public IForkCommandResponse execute( IForkCommand command ) throws ProcessException;

}
