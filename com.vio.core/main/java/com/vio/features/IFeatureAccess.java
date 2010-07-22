package com.vio.features;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 9:52:24 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureAccess<T extends IFeatureInteractor> {

    public void setInteractor( T interactor );

    public <T extends IFeatureInteractor> T getInteractor();

    public String getFeatureName();

    public void setFeatureName( String name );

    public String getAspectName();

    public void setAspectName( String name );

    public boolean isAffectable( IFeatureAspect aspect );
}
