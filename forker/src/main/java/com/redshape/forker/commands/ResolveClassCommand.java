package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;
import com.redshape.utils.Commons;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {4:33 PM}
 */
public final class ResolveClassCommand {

    public static class Response extends AbstractForkCommandResponse {
        private String canonicalName;
        private byte[] clazzData;

        public Response(Status status) {
            super(Commands.RESOLVE_CLASS_RSP, status);
        }

        public String getCanonicalName() {
            return canonicalName;
        }

        public void setCanonicalName(String canonicalName) {
            this.canonicalName = canonicalName;
        }

        public byte[] getClazzData() {
            return clazzData;
        }

        public void setClazzData(byte[] clazzData) {
            this.clazzData = clazzData;
        }

        @Override
        public void readFrom(DataInputStream stream) throws IOException {
            this.canonicalName = stream.readUTF();
            int length = stream.readInt();
            this.clazzData = new byte[length];
            stream.readFully(clazzData, 0, length);
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
            stream.writeUTF( this.canonicalName );
            stream.writeInt( this.clazzData.length );
            stream.write( this.clazzData, 0, this.clazzData.length );
        }
    }

    public static class Request extends AbstractForkCommand {

        private String canonicalName;

        public Request() {
            super( Commands.RESOLVE_CLASS, Commands.RESOLVE_CLASS_RSP );
        }

        public void setCanonicalName(String canonicalName) {
            Commons.checkNotNull(canonicalName);
            this.canonicalName = canonicalName;
        }

        public String getCanonicalName() {
            return canonicalName;
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
            this.canonicalName = input.readUTF();
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
            output.writeUTF( this.canonicalName );
        }
    }
}
