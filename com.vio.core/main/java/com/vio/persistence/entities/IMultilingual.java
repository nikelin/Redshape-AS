package com.vio.persistence.entities;

import com.vio.persistence.managers.ManagerException;

import java.util.Set;

/**
 * Interface for persistence objects
 * that has locale-dependent fields which can be represented
 * in other locales
 */
public interface IMultilingual<T extends IMultilingual> extends IEntity {

    /**
     * Check for translations of current entity to given locale
     * @param locale
     * @return
     */
    public boolean hasTranslation( Locale locale) throws ManagerException;

    /**
     * Get translation for current object in given locale
     * @param locale
     * @return
     * @throws ManagerException
     */
    public T getTranslation( Locale locale ) throws ManagerException;

    /**
     * Get locale for current entity
     * @return
     */
    public Locale getLocale();

    /**
     * Change locale for current entity
     * @param locale
     */
    public void setLocale( Locale locale);

    /**
     * Get original entity ( to introduce relation between same objects in different locales )
     * @return
     */
    public T getOriginal();

    /**
     * Set original entity
     * @param original
     */
    public void setOriginal( T original );

    /**
     * Get all available versions in other locales for the current object
     * @return
     */
    public Set<T> getVersions();

}
