package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: cyril
 * Date: 8/27/12
 * Time: 4:18 PM
 * To change this template use File | Settings | File Templates.
 */
public class InitResponse extends AbstractForkCommandResponse {

    public InitResponse(Status status) {
        super(Commands.INIT_RSP, status);
    }

    @Override
    public void readFrom(DataInputStream stream) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void writeTo(DataOutputStream stream) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
