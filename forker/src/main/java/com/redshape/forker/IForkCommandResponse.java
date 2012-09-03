package com.redshape.forker;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @date 1/31/12 {4:49 PM}
 */
public interface IForkCommandResponse {

    public enum Status {
        FAIL,
        SUCCESS,
        QUEUED
    }

    public Long getQualifier();

    public void setQualifier( Long qualifier );

    public Long getId();

    public Status getStatus();

    public void readFrom( DataInputStream stream ) throws IOException;

    public void writeTo( DataOutputStream stream ) throws IOException;
    
}
