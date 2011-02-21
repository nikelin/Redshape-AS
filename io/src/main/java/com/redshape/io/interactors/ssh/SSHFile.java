package com.redshape.io.interactors.ssh;

import net.schmizz.sshj.sftp.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import com.redshape.io.IFilesystemNode;
import com.redshape.utils.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: root
 * Date: Nov 8, 2010
 * Time: 6:43:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class SSHFile implements IFilesystemNode {
    private RemoteFile file;
    private SFTPClient sftpClient;
    private String[] nameParts;
    private String parentPath;

    public SSHFile( SFTPClient sftpClient, RemoteFile file ) {
        this.sftpClient = sftpClient;
        this.setFile(file);
    }

    protected SFTPClient getSFTPClient() {
        return this.sftpClient;
    }

    protected void setFile( RemoteFile file ) {
        this.file = file;
        this.nameParts = this.getFile().getPath().split("/");
        this.parentPath = StringUtils.join(
            Arrays.asList( this.nameParts )
                  .subList(
                        0, this.nameParts.length == 1 ? 1 : this.nameParts.length - 1
                  ),
            "/" );
    }

    protected RemoteFile getFile() {
        return this.file;
    }

    public String getName() {
        return this.nameParts[this.nameParts.length - 1];
    }

    public InputStream getInputStream() {
        return this.getFile().getInputStream();
    }

    public OutputStream getOutputStream() {
        return this.getFile().getOutputStream();
    }

    public String getCanonicalPath() {
        return this.getFile().getPath();
    }

    public boolean isExists() throws IOException {
        return !this.getSFTPClient().type( this.getCanonicalPath() ).equals( FileMode.Type.UNKNOWN );
    }

    public boolean isDirectory() throws IOException {
        return this.getSFTPClient().type( this.getCanonicalPath() ).equals( FileMode.Type.DIRECTORY );
    }

    public boolean isFile() throws IOException {
        return this.getSFTPClient().type( this.getCanonicalPath() ).equals( FileMode.Type.REGULAR );
    }

    public String[] list() throws IOException {
        Collection<String> result = new HashSet<String>();
        for ( RemoteResourceInfo info : this.getSFTPClient().ls( this.getCanonicalPath() ) ) {
            result.add( info.getName() );
        }

        return result.toArray( new String[ result.size() ] );
    }

    public String getParent() {
        return this.parentPath;
    }

    public void close() throws IOException  {
        this.getFile().close();
    }

    public void remove() throws IOException {
        this.getSFTPClient().rm( this.getCanonicalPath() );
    }

    public void createNew() throws IOException {
        this.setFile( this.getSFTPClient().open( this.getCanonicalPath(), this.createAttributesSet(OpenMode.CREAT, OpenMode.WRITE) ) );
    }

    protected Set<OpenMode> createAttributesSet( OpenMode... modes ) {
        HashSet<OpenMode> result = new HashSet();
        for ( OpenMode mode : modes ) {
            result.add(mode);
        }

        return result;
    }
}