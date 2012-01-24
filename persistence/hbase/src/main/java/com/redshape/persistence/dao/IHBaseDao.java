/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.persistence.dao;

import com.redshape.persistence.entities.IEntity;

/**
 *
 * @author user
 */
public interface IHBaseDao<T extends IEntity> extends IDAO<T> {

    public IIndexBuilder getIndexBuilder();

    public void setIndexBuilder(IIndexBuilder indexBuilder);

    public ISerializer getEntitySerializer();

    public void setEntitySerializer(ISerializer serializer);

    public ISerializer getFieldsSerializer();

    public void setFieldsSerializer(ISerializer serializer);

}
