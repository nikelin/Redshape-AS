package com.vio.features;

import java.util.Collection;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:19:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureInteractorsGroup<T extends IFeatureInteractor> {

    public Set<T> getInteractors();

    public void setInteractors( Set<T> interactors );

    public void addInteractor( T interactor );

    public void removeInteractor( T interactor );

}
