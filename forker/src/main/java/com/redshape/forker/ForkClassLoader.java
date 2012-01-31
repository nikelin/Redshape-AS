package com.redshape.forker;

import com.redshape.forker.commands.FindResourceCommand;
import com.redshape.forker.commands.FindResourcesCommand;
import com.redshape.forker.commands.ResolveClassCommand;
import com.redshape.forker.protocol.IForkProtocol;
import com.redshape.utils.Commons;

import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @inspiredBy Jukka Zitting <jukkaz.wordpress.com>
 * @package com.redshape.forker
 * @date 1/31/12 {7:40 PM}
 */
public class ForkClassLoader extends ClassLoader {

    private int resourcesCount = 0;
    
    private DataInputStream input;
    private DataOutputStream output;
    private IForkProtocol protocol;

    private Object protocolLock = new Object();
    
    public ForkClassLoader( IForkProtocol protocol, DataInputStream input, DataOutputStream output ) {
        Commons.checkNotNull(input);
        Commons.checkNotNull(output);
        Commons.checkNotNull(protocol);

        this.protocol = protocol;
        this.input = input;
        this.output = output;
    }

    public int getResourcesCount() {
        return resourcesCount;
    }

    protected IForkProtocol getProtocol() {
        return this.protocol;
    }

    protected DataInputStream getInput() {
        return this.input;
    }

    protected DataOutputStream getOutput() {
        return output;
    }

    @Override
    protected URL findResource(String name)  {
        synchronized (protocolLock) {
            try {
                this.getProtocol().writeCommand( this.getOutput(), new FindResourceCommand.Request(name) );
                return this.saveFile(
                    this.getProtocol().
                        <FindResourceCommand.Response>readResponse(this.getInput())
                        .getData()
                ).toURI().toURL();
            } catch ( IOException e ) {
                throw new IOError(e);
            }
        }
    }

    protected File saveFile( byte[] data ) throws IOException {
        File file = new File( this.generateFileName() );

        OutputStream stream = new FileOutputStream(file);
        try {
            stream.write(data);
        } finally {
            stream.close();
        }

        return file;
    }
    
    protected String generateFileName() {
        return "resource-" + this.getResourcesCount() + "-.bin";
    }

    @Override
    protected Enumeration<URL> findResources(String name) throws IOException {
        synchronized ( this.protocolLock ) {
            this.getProtocol().writeCommand( this.getOutput(), new FindResourcesCommand.Request(name) );

            FindResourcesCommand.Response response = this.getProtocol().readResponse(this.getInput());

            List<URL> resources = new ArrayList<URL>();
            for (URI uri : response.getResources() ) {
                resources.add( this.findResource(uri.toString()) );
            }
            
            return Collections.enumeration(resources);
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        synchronized (this.protocol) {
            try {
                this.getProtocol().writeCommand( this.getOutput(), new ResolveClassCommand.Request(name) );
                
                ResolveClassCommand.Response response = this.getProtocol().readResponse( this.getInput() );

                return this.defineClass(name, response.getClazzData(), 0, response.getClazzData().length);
            } catch ( IOException e ) {
                throw new ClassNotFoundException( e.getMessage(), e );
            }
        }
    }

}
