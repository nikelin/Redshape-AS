package com.redshape.features;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:19:53 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureInteractorsGroup {

    public <T extends IFeatureInteractor> Set<T> getInteractors();

    public void setInteractors( Set<IFeatureInteractor> interactors );

    public void addInteractor( IFeatureInteractor interactor );

    public void removeInteractor( IFeatureInteractor interactor );

}
