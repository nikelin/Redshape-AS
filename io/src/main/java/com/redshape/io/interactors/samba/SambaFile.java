/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.redshape.io.interactors.samba;

import com.redshape.io.IFilesystemNode;
import com.redshape.io.NetworkInteractionException;
import jcifs.smb.SmbFile;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


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

	@Override
	public void mkdir() throws IOException {
		this.getContext().mkdir();
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
