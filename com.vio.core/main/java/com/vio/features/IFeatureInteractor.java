package com.vio.features;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:15:42 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureInteractor<T> {

    public T getInteractorsGroup();

    public void setInteractorsGroup( T group );

    public boolean canInteract( IFeatureAspect aspect );

    public void grantAccess( IFeatureAspect aspect );

    public void revokeAccess( IFeatureAspect aspect );
}
