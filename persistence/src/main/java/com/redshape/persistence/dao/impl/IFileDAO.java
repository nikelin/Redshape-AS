package com.redshape.persistence.dao.impl;

import java.util.Collection;

import com.redshape.persistence.dao.IDAO;
import com.redshape.persistence.entities.files.File;

public interface IFileDAO extends IDAO<File> {
	
	public Collection<File> findByName( String name );
	
}
