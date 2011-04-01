package com.redshape.bootstrap;

import com.redshape.api.InvokableEntitiesRegistry;
import com.redshape.applications.bootstrap.AbstractBootstrapAction;

import com.redshape.applications.bootstrap.BootstrapException;

import com.redshape.features.FeaturesRegistry;
import com.redshape.renderer.AbstractRenderersFactory;
import com.redshape.utils.config.IConfig;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 17, 2010
 * Time: 12:14:17 PM
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ApiInit extends AbstractBootstrapAction {
    private static final Logger log = Logger.getLogger( ApiInit.class );
    
    public ApiInit() {
        this.setId("api");
    }

    public void process() throws BootstrapException {
        try {
            for ( IConfig featureNode : this.getConfig().get("features").childs() ) {
                FeaturesRegistry.getDefault().addFeaturesPackage( featureNode.value() );
            }

            FeaturesRegistry.getDefault().init();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new BootstrapException("Interfaces registration exception");
        }
    }

}
