package com.redshape.forker.commands;

import com.redshape.forker.AbstractForkCommand;
import com.redshape.forker.AbstractForkCommandResponse;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.forker.commands
 * @date 1/31/12 {4:33 PM}
 */
public final class FindResourcesCommand {

    private static final Logger log = Logger.getLogger(FindResourcesCommand.class);
    
    public static class Request extends AbstractForkCommand {
        public static final long ID = Request.class.getCanonicalName().hashCode();

        private String path;
        
        public Request( String path ) {
            super( Request.ID, Response.ID);
        }
        
        protected String getPath() {
            return this.path;
        }

        @Override
        public void writeTo(DataOutputStream output) throws IOException {
            output.writeUTF(this.path);
        }

        @Override
        public void readFrom(DataInputStream input) throws IOException {
            this.path = input.readUTF();
        }
    }

    public static class Response extends AbstractForkCommandResponse {
        public static final long ID = Response.class.getCanonicalName().hashCode();

        private List<URI> resources = new ArrayList<URI>();
        
        public Response(Status status) {
            super(Response.ID, status);
        }

        public List<URI> getResources() {
            return this.resources;
        }
        
        @Override
        public void readFrom(DataInputStream stream) throws IOException {
            int count = stream.readInt();
            if ( count <= 0 ) { 
                return;
            }

            this.resources.clear();;
            for ( int i = 0; i < count; i++ ) {
                try {
                    this.resources.add(new URI(stream.readUTF()));
                } catch ( URISyntaxException e ) {
                    log.error( e.getMessage(), e );
                    continue;
                }
            }
        }

        @Override
        public void writeTo(DataOutputStream stream) throws IOException {
            stream.writeShort( this.getResources().size() );
            for ( URI resource : this.getResources() ) {
                stream.writeUTF( resource.toString() );
            }
        }
    }
}
