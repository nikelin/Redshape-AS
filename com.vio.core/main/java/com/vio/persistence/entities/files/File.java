package com.vio.persistence.entities.files;

import com.vio.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:22:12 PM
 * To change this template use File | Settings | File Templates.
 */
@MappedSuperclass
public abstract class File extends AbstractEntity<File> {

    @Basic
    private String filename;

    @Basic
    private FileMeta fileMeta;

    public void setFileName( String name ) {
        this.filename = name;
    }

    public String getFileName() {
        return this.filename;
    }
    
    public void setMeta( FileMeta meta ) {
        this.fileMeta = meta;
    }

    public FileMeta getMeta() {
        return this.fileMeta;
    }

}
