package com.redshape.persistence.entities.files;

import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Aug 6, 2010
 * Time: 6:27:40 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity( name = "files_meta" )
public class FileMeta extends AbstractEntity<FileMeta> {

    @Basic
    private Long filesize;

    @Basic
    private String extension;

    @Basic
    private String mimetype;

    @Basic
    private Long createdTime;

    @Basic
    private Long lastAccessTime;

    @Basic
    private Long lastUpdatedTime;

    
}
