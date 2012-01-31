package com.redshape.forker.protocol;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.protocol
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {7:00 PM}
 */
public interface IForkProtocol {

    public <C extends IForkCommand> C readCommand( DataInputStream stream ) throws IOException;
    
    public void writeCommand( DataOutputStream stream, IForkCommand command ) throws IOException;
    
    public <R extends IForkCommandResponse> R readResponse( DataInputStream stream ) throws IOException;
    
    public void writeResponse( DataOutputStream stream, IForkCommandResponse response ) throws IOException;
    
}
