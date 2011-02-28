package com.redshape.features.example;

import com.redshape.features.AbstractFeatureAspect;
import com.redshape.features.IFeatureInteractor;
import com.redshape.features.InteractionException;
import com.redshape.features.InteractionResult;
import com.redshape.features.annotations.FeatureAspect;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 7, 2010
 * Time: 12:01:44 PM
 * To change this template use File | Settings | File Templates.
 */
@FeatureAspect( name = "echo", feature = "example" )
public class EchoAction extends AbstractFeatureAspect {

    @Override
    protected InteractionResult processInteraction( IFeatureInteractor interactor ) throws InteractionException {
        InteractionResult response = new InteractionResult();
        
        response.setAttribute( "response", this.getAttribute("request") );

        return response;
    }

}
