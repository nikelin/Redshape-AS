package com.redshape.persistence.jms;

import com.redshape.persistence.entities.AbstractEntity;

import javax.persistence.Basic;
import javax.persistence.Entity;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.persistence.jms
 * @date 1/25/12 {4:27 PM}
 */
@Entity( name = "test_entities" )
public class TestEntity extends AbstractEntity {

    @Basic
    private String name;

    public TestEntity() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
