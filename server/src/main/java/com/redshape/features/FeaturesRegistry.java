package com.redshape.features;

import com.redshape.features.annotations.FeatureAspect;
import com.redshape.utils.InterfacesFilter;
import com.redshape.utils.PackageLoaderException;
import com.redshape.utils.PackagesLoader;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 4:22:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FeaturesRegistry implements IFeaturesRegistry {
    private static final Logger log = Logger.getLogger( FeaturesRegistry.class );
    private static IFeaturesRegistry defaultInstance = new FeaturesRegistry();

    /**
     * Packages where features and aspects would be searching in
     */
    private Set<String> featuresPackages = new HashSet<String>();

    /**
     * Registry of features and aspected related them
     */
    private Map<Class<? extends IFeatureAspect>, IFeatureAspect> features = new HashMap();

    private PackagesLoader packagesLoader;
    
    public static IFeaturesRegistry getDefault() {
        return defaultInstance;
    }

    public static void setDefault( IFeaturesRegistry instance ) {
        defaultInstance = instance;
    }

    private FeaturesRegistry() {
        this.loadPackages();
    }
    
    public void setPackagesLoader( PackagesLoader loader ) {
    	this.packagesLoader = loader;
    }
    
    public PackagesLoader getPackagesLoader() {
    	return this.packagesLoader;
    }

    @Override
    public void addFeatureAspect( IFeatureAspect feature ) {
        this.features.put( feature.getClass(), feature );
    }

    @Override
    public Class<? extends IFeatureAspect>[] getFeatures() {
        return this.features.values().toArray( new Class[ this.features.size() ] );
    }

    @Override
    public <T extends IFeatureAspect> T getFeatureAspect( Class<T> featureClass ) {
        return (T) this.features.get(featureClass);
    }

    @Override
    public <T extends IFeatureAspect> T getFeatureAspect( String featureName, String aspectName ) {
        T result = null;
        for ( Class<? extends IFeatureAspect> aspectClass : this.features.keySet() ) {
            FeatureAspect aspectAnnotation = aspectClass.getAnnotation( FeatureAspect.class );
            if ( aspectAnnotation.feature().equals( featureName ) &&
                                            aspectAnnotation.name().equals( aspectName ) ) {
                result = (T) this.features.get(aspectClass);
            }
        }

        return result;
    }

    @Override
    public void addFeaturesPackage( String path ) {
        this.featuresPackages.add(path);
    }

    public void init() {
        this.loadPackages();
    }

    protected Set<String> getFeaturesPackages() {
        return this.featuresPackages;
    }

    protected void loadPackages() {
        for ( String path : this.getFeaturesPackages() ) {
            try {
                this.processPackage(path);
            } catch ( Throwable e ) {
                log.error("Unable to process package " + path + " correctly.");
            }
        }
    }
    
    private void processPackage( String packagePath ) throws PackageLoaderException, InstantiationException {
        log.info("Loading features from " + packagePath );
        Class<? extends IFeatureAspect>[] classes = this.getPackagesLoader()
                                                        .<IFeatureAspect>getClasses( packagePath, new InterfacesFilter(
                                                            new Class[] { IFeatureAspect.class },
                                                            new Class[] { FeatureAspect.class },
                                                            true
                                                        ) );
        
        log.info("Total features founds: " + classes.length );

        for ( Class<? extends IFeatureAspect> featureAspectClass : classes ) {
            log.info( "Processing feature entity: " + featureAspectClass.getCanonicalName() );

            this.features.put( featureAspectClass, this.createAspect(featureAspectClass) );
        }
    }

    protected IFeatureAspect createAspect( Class<? extends IFeatureAspect> aspectClass ) throws InstantiationException {
        try {
            FeatureAspect aspectAnnotation = aspectClass.getAnnotation(FeatureAspect.class);

            IFeatureAspect aspect = aspectClass.newInstance();
            aspect.setAspectName( aspectAnnotation.name() );
            aspect.setFeatureName( aspectAnnotation.feature() );

            return aspect;
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }
    }
}
