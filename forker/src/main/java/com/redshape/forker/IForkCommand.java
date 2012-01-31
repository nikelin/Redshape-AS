package com.redshape.forker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {1:52 PM}
 */
public interface IForkCommand {

    /**
     * Return identifier which will be used by a protocol serializer
     * to indicates command response object in process output stream.
     *
     * @return
     */
    public Long getResponseId();

    /**
     * Return identifier which will be used by a protocol serializer
     * to indicates command object in process input stream.
     * @return
     */
    public Long getCommandId();

    public void readFrom( DataInputStream input ) throws IOException;

    public void writeTo( DataOutputStream output ) throws IOException;

}
