package com.redshape.features;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Oct 5, 2010
 * Time: 2:08:38 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeaturesRegistry {
    void addFeatureAspect( IFeatureAspect feature );

    public Class<? extends IFeatureAspect>[] getFeatures();

    public <T extends IFeatureAspect> T getFeatureAspect( Class<T> featureClass );

    public <T extends IFeatureAspect> T getFeatureAspect( String featureName, String aspectName );

    public void addFeaturesPackage( String path );
    
    public void init();
}
