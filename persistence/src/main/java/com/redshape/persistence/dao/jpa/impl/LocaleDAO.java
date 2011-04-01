package com.redshape.persistence.dao.jpa.impl;

import com.redshape.persistence.dao.impl.ILocaleDAO;
import com.redshape.persistence.dao.jpa.AbstractJPADAO;
import com.redshape.persistence.entities.Locale;

public class LocaleDAO extends AbstractJPADAO<Locale> implements ILocaleDAO {
	
	public LocaleDAO() {
		super( Locale.class );
	}
}
