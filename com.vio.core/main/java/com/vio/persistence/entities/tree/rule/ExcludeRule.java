package com.vio.persistence.entities.tree.rule;

import com.vio.persistence.entities.Entity;
import com.vio.persistence.entities.tree.InheritanceRule;
import com.vio.persistence.managers.ManagerException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.MappedSuperclass;

/**
 * Конкретизация InheritanceRule для реализации исключения наследуемой
 * сущности
 * @param <V> Наследуемая сущность
 * @param <T> Наследующая сущность
 */
@MappedSuperclass
abstract public class ExcludeRule<V extends Entity, T extends Entity> extends InheritanceRule<V, T> {

    @Override
    public boolean isSatisfied( V entity ) throws ManagerException {
        return this.getSubjects().contains(entity);
    }

}