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
public class FileMeta extends AbstractEntity {
	private static final long serialVersionUID = 7441294261584686877L;

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

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public String getMimetype() {
		return mimetype;
	}

	public void setMimetype(String mimetype) {
		this.mimetype = mimetype;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Long getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Long lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}

}
