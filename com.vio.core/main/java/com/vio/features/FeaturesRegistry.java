package com.vio.features;

import com.vio.features.annotations.FeatureAspect;
import com.vio.utils.InterfacesFilter;
import com.vio.utils.PackageLoaderException;
import com.vio.utils.Registry;
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
public class FeaturesRegistry {
    private static final Logger log = Logger.getLogger( FeaturesRegistry.class );
    private static FeaturesRegistry defaultInstance = new FeaturesRegistry();

    /**
     * Packages where features and aspects would be searching in
     */
    private Set<String> featuresPackages = new HashSet();

    /**
     * Registry of features and aspected related them
     */
    private Map<Class<? extends IFeatureAspect>, IFeatureAspect> features = new HashMap();

    public static FeaturesRegistry getDefault() {
        return defaultInstance;
    }

    public static void setDefault( FeaturesRegistry instance ) {
        defaultInstance = instance;
    }

    private FeaturesRegistry() {
        this.loadPackages();
    }

    public void addFeatureAspect( IFeatureAspect feature ) {
        this.features.put( feature.getClass(), feature );
    }

    public <T extends IFeatureAspect> T getFeatureAspect( Class<T> featureClass ) {
        return (T) this.features.get(featureClass);
    }

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

    public void addFeaturesPackage( String path ) {
        this.featuresPackages.add(path);
    }

    protected Set<String> getFeaturesPackages() {
        return this.featuresPackages;
    }

    public void loadPackages() {
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
        Class<? extends IFeatureAspect>[] classes = Registry.getPackagesLoader()
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
