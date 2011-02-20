package com.redshape.features;

import com.redshape.io.net.request.IRequest;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 3:14:50 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFeatureAspect {

    public String getFeatureName();

    public void setFeatureName( String name );

    public String getAspectName();

    public void setAspectName( String name );

    public IInteractionResult interact( IFeatureInteractor interactor ) throws InteractionException;

    public void setAttribute( String name, Object value );

    public <V> V getAttribute( String name );

    public boolean hasAttribute( String name );

    public boolean isValid();

    public IRequest getRequest();

    public void setRequest( IRequest request );

    public IInteractionResult createResultObject();
    
}
