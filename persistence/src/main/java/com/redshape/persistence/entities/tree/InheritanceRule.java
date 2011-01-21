package com.redshape.persistence.entities.tree;

import javax.persistence.*;

import com.redshape.persistence.dao.ManagerException;
import com.redshape.persistence.entities.AbstractEntity;
import com.redshape.persistence.entities.IEntity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Абстрактное правило наследования сущностью T сущности V
 * @param <V> Наследуемая сущность
 * @param <T> Сущность наследник
 */
@MappedSuperclass
abstract public class InheritanceRule<V extends IEntity, T extends IEntity> extends AbstractEntity {

    @OneToMany
    private Set<V> subjects = new HashSet<V>();

    @ManyToOne( cascade = { CascadeType.REMOVE } )
    @JoinColumn( name = "ancestor_id" )
    private T ancestor;

    public InheritanceRule() {}

    public T getAncestor() {
        return this.ancestor;
    }

    public void setAncestor( T entity ) {
        this.ancestor = entity;
    }

    public Set<V> getSubjects() {
        return this.subjects;
    }

    public void addSubject( V subject ) {
        this.subjects.add(subject);
    }

    public void addSubjects( V[] subject ) {
        this.subjects.addAll( Arrays.asList( subject ) );
    }

    abstract public boolean isSatisfied( V entity ) throws ManagerException;
}
