package com.redshape.forker.protocol;

import com.redshape.forker.IForkCommand;
import com.redshape.forker.IForkCommandResponse;

import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.protocol
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {7:00 PM}
 */
public interface IForkProtocol {

    public enum TokenType {
        COMMAND,
        RESPONSE;
    }

    public TokenType matchToken() throws IOException;

    public <C extends IForkCommand> C readCommand() throws IOException;
    
    public void writeCommand( IForkCommand command ) throws IOException;
    
    public <R extends IForkCommandResponse> R readResponse() throws IOException;
    
    public void writeResponse( IForkCommandResponse response ) throws IOException;
    
}
