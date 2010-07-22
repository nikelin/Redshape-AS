package com.vio.features;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:19:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureInteractorsGroup<T extends IFeatureInteractor> {

    public Collection<T> getInteractors();

    public void addInteractor( T interactor );

    public void removeInteractor( T interactor );

}
