package com.vio.io.protocols.vanilla.request;

import com.vio.io.protocols.core.request.IRequest;
import com.vio.persistence.entities.requesters.IRequester;
import com.vio.render.Renderable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IAPIRequest extends IRequest, Renderable {

    public void setId( String id );

    public String getId();

    /**
     * In a case of composite request - return current request parent,
     * or NULL if its root request object
     * 
     * @return
     */
    public IAPIRequest getParent();

    public void setParent( IAPIRequest request );

    /**
     * Get action of current response
     * @return
     */
    public String getAspectName();

    public void setAspectName( String aspectName );

    public void setFeature( String featureName );

    public String getFeature();

    public Object getParam( String name );

    public void setParams( Map<String, Object> params );

    public Map<String, Object> getParams();

    public boolean hasParam( String name );

    public boolean hasChilds();

    public void setChildren( Collection<IAPIRequest> body );

    public void addChild( IAPIRequest invoke );

    public Collection<IAPIRequest> getChildren();

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
