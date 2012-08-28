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
 * Time: 1:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ErrorResponse extends AbstractForkCommandResponse {

    private String details;

    public ErrorResponse(String details, Status status) {
        super(Commands.ERROR_RSP, status);

        this.details = details;
    }

    @Override
    public void readFrom(DataInputStream stream) throws IOException {
        this.details = stream.readUTF();
    }

    @Override
    public void writeTo(DataOutputStream stream) throws IOException {
        stream.writeUTF( this.details );
    }
}
