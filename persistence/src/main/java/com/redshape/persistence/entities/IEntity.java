package com.redshape.persistence.entities;

import java.io.Serializable;

/**
 * Интерфейс для представления абстрактного объекта из хранилища постоянных
 * объектов
 *
 * @author nikelin
 */
public interface IEntity extends Serializable {

    public Integer getId();

    public void setId( Integer id );

}
