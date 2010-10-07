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

    Class<? extends IFeatureAspect>[] getFeatures();

    <T extends IFeatureAspect> T getFeatureAspect( Class<T> featureClass );

    <T extends IFeatureAspect> T getFeatureAspect( String featureName, String aspectName );

    void addFeaturesPackage( String path );

    void init();
}
