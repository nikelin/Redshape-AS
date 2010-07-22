package com.vio.applications.bootstrap.actions;

import com.vio.applications.bootstrap.AbstractBootstrapAction;
import com.vio.applications.bootstrap.BootstrapException;
import com.vio.features.FeaturesRegistry;
import com.vio.render.RenderersFactory;
import com.vio.utils.Registry;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 17, 2010
 * Time: 12:14:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApiInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( ApiInit.class );

    public ApiInit() {
        this.setId("api");
    }

    public void process() throws BootstrapException {
        try {
            for( String featurePackage : Registry.getApiServerConfig().getFeaturesPackages() ) {
                FeaturesRegistry.getDefault().addFeaturesPackage(featurePackage);
            }

            FeaturesRegistry.getDefault().loadPackages();

            RenderersFactory.getDefault();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException("Interfaces registration exception");
        }
    }

}
