package com.redshape.io.interactors.samba;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;

import org.apache.log4j.Logger;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.NetworkInteractionException;


/**
 * Adapter to
 * @author nikelin
 */
public class SambaFile implements IFilesystemNode {
    private static final Logger log = Logger.getLogger( SambaFile.class );
    private SmbFile context;

    public SambaFile( SmbFile file ) {
        this.context = file;
    }

    protected SmbFile getContext() {
        return this.context;
    }

    @Override
    public void createNew() throws NetworkInteractionException {
        try {
            this.getContext().createNewFile();
        } catch ( IOException e ) {
            throw new NetworkInteractionException();
        }
    }

    @Override
    public String getParent() throws IOException {
        return this.getContext().getParent();
    }

    @Override
    public boolean isFile() throws IOException {
        return this.getContext().isFile();
    }

    @Override
    public boolean isDirectory() throws IOException {
        return this.getContext().isDirectory();
    }

    @Override
    public boolean isExists() throws IOException {
        return this.getContext().exists();
    }

    @Override
    public String[] list() throws IOException {
        return this.getContext().list();
    }

    @Override
    public String getCanonicalPath() {
        return this.getContext().getCanonicalPath();
    }

    @Override
    public String getName() {
        return this.getContext().getName();
    }

    @Override
    public void remove() throws IOException {
        this.getContext().delete();
    }

    @Override
    public OutputStream getOutputStream() throws IOException {
        try {
            return this.getContext().getOutputStream();
        } catch ( IOException e ) {
            throw new NetworkInteractionException();
        }
    }

    @Override
    public InputStream getInputStream() throws IOException {
        try {
            return this.getContext().getInputStream();
        } catch ( IOException e ) {
            throw new NetworkInteractionException();
        }
    }

    /**
         * @todo Needs to investigate
         * @throws NetworkInteractionException
         */
    @Override
    public void close() throws IOException {
        log.info("close() method not implemented");
    }

}
