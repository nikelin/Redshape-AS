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

    public <T extends IFeatureInteractor> void setInteractors( Set<T> interactors );

    public <T extends IFeatureInteractor> void addInteractor( T interactor );

    public <T extends IFeatureInteractor> void removeInteractor( T interactor );

}
