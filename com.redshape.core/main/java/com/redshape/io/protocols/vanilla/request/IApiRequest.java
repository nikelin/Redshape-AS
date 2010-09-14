package com.redshape.io.protocols.vanilla.request;

import com.redshape.io.protocols.core.request.IRequest;
import com.redshape.io.protocols.core.request.RequestType;
import com.redshape.persistence.entities.requesters.IRequester;
import com.redshape.render.IRenderable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IApiRequest extends IRequest, IRenderable {

    public RequestType getType();

    public void setType( RequestType type );

    public void setId( String id );

    public String getId();

    /**
     * In a case of composite request - return current request parent,
     * or NULL if its root request object
     * 
     * @return
     */
    public IApiRequest getParent();

    public void setParent( IApiRequest request );

    /**
     * Get action of current response
     * @return
     */
    public String getAspectName();

    public void setAspectName( String aspectName );

    public void setFeatureName( String featureName );

    public String getFeatureName();

    public Object getParam( String name );

    public void setParams( Map<String, Object> params );

    public Map<String, Object> getParams();

    public boolean hasParam( String name );

    public boolean hasChilds();

    public void setChildren( Collection<IApiRequest> body );

    public void addChild( IApiRequest invoke );

    public Collection<IApiRequest> getChildren();

    public IRequester getIdentity();

    public void setIdentity( IRequester identity );

    public boolean isAsync();

    public Map<String, Object> getMap( String name );

    public String getString( String name );

    public List<Object> getList( String name );

    public Integer getInteger( String name );

    public boolean isValid();

    public void markInvalid( boolean state );

    public void addParam( String name, Object value );
    
}
