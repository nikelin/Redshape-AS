package com.redshape.persistence;

import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.persistence.entities.IDTO;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 1/23/12
 * Time: 4:31 PM
 * To change this template use File | Settings | File Templates.
 */
@Entity( name = "test_entities" )
public class TestEntity extends AbstractEntity {

    @Basic
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public IDTO createDTO() {
        return null;
    }

}
