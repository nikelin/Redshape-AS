package com.redshape.forker.protocol.queue;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;
import com.redshape.utils.IFilter;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/29/12
 * Time: 3:11 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IProtocolQueue {

    public boolean hasRequest();

    public boolean hasResponse();

    public void collectRequest( IForkCommand command );

    public IForkCommand pollRequest();

    public IForkCommand peekRequest();

    public IForkCommand peekRequest( IFilter<IForkCommand> filter );

    public void collectResponse( IForkCommandResponse response );

    public IForkCommandResponse pollResponse();

    public IForkCommandResponse peekResponse();

    public IForkCommandResponse peekResponse( IFilter<IForkCommandResponse> filter );

}
