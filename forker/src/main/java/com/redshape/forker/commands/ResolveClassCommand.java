package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;
import com.redshape.forker.Commands;

import java.io.ByteArrayOutputStream;
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
        private boolean success;
        private byte[] clazzData;

        public Response(Long id, Status status) {
            super(id, status);
        }

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public byte[] getClazzData() {
            return clazzData;
        }

        public void setClazzData(byte[] clazzData) {
            this.clazzData = clazzData;
        }

        @Override
        public void readFrom(DataInputStream stream) throws IOException {
            this.success = stream.readBoolean();

            ByteArrayOutputStream result = new ByteArrayOutputStream();

            int count;
            byte[] data = new byte[0xffff];
            while ( ( count = stream.readUnsignedShort() ) > 0 ) {
                stream.readFully(data, 0, count);
                result.write( data, 0, count );
            }

            this.clazzData = result.toByteArray();
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    public static class Request extends AbstractForkCommand {

        private String canonicalName;

        public Request( String canonicalName ) {
            super( Commands.RESOLVE_CLASS, Commands.RESOLVE_CLASS_RSP );

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
