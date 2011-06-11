package com.redshape.persistence.dao;

import com.redshape.persistence.dao.jpa.AbstractJPADAO;
import com.redshape.persistence.entities.IMultilingual;
import com.redshape.persistence.entities.Locale;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 24, 2010
 * Time: 4:54:26 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractMultilingualManager<T extends IMultilingual<T>> extends AbstractJPADAO<T> {

    public AbstractMultilingualManager( Class<T> entity ) {
        super( entity );
    }

    // @FIXME: due to dao refactorings
    public <V extends IMultilingual<T>> boolean hasTranslation( T entity, Locale locale ) throws ManagerException {
        return false;
    }

 // @FIXME: due to dao refactorings
    public <V extends IMultilingual<T>> T getTranslation( T entity, Locale locale ) throws ManagerException {
       return null;
    }

}
