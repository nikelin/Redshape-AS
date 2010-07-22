package com.vio.persistence.entities.tree;

import javax.persistence.*;

import com.vio.persistence.entities.AbstractEntity;
import com.vio.persistence.entities.Entity;
import com.vio.persistence.managers.ManagerException;
import com.vio.utils.PackageLoader;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Абстрактное правило наследования сущностью T сущности V
 * @param <V> Наследуемая сущность
 * @param <T> Сущность наследник
 */
@MappedSuperclass
abstract public class InheritanceRule<V extends Entity, T extends Entity> extends AbstractEntity<InheritanceRule> {

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
