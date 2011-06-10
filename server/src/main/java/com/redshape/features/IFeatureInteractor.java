package com.redshape.features;

import com.redshape.api.requesters.IRequester;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:15:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureInteractor<V extends IFeatureInteractor<V>> extends IRequester {

    public <T extends IFeatureInteractorsGroup<V>> T getInteractorsGroup();

    public void setInteractorsGroup( IFeatureInteractorsGroup<V> group );

    public boolean canInteract( IFeatureAspect aspect );

    public void grantAccess( IFeatureAspect aspect );

    public void revokeAccess( IFeatureAspect aspect );
}
