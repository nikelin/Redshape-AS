package com.redshape.features;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.redshape.io.net.request.IRequest;
import com.redshape.utils.config.IConfig;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 5:45:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFeatureAspect implements IFeatureAspect {
    private static final Logger log = Logger.getLogger( AbstractFeatureAspect.class );

    private Map<String, Object> attributes = new HashMap<String, Object>();
    
    private String featureName;
    
    private String aspectName;
    
    public IRequest request;
    
    @Autowired( required = true )
    private IConfig config;
    
    public void setConfig( IConfig config ) {
    	this.config = config;
    }
    
    public IConfig getConfig() {
    	return this.config;
    }

    @Override
    public IInteractionResult interact( IFeatureInteractor requester ) throws InteractionException {
        try {
            for ( Method method : this.getClass().getMethods() ) {
                if ( !method.getName().equals( "interact" ) ) {
                    continue;
                }

                Class<?> parameterTypes[] = method.getParameterTypes();
                if ( parameterTypes.length != 2 ) {
                    continue;
                }

                if ( parameterTypes[0].isAssignableFrom( this.getRequest().getClass() )
                        && parameterTypes[1].isAssignableFrom( requester.getClass() ) ) {
                    return (IInteractionResult) method.invoke( this, this.getRequest(), requester );
                }
            }

            return this.processInteraction( requester );
        } catch ( Throwable e ) {
            log.error(e.getMessage(), e);
            throw new InteractionException( e.getMessage(), e );
        }
    }

    abstract protected IInteractionResult processInteraction( IFeatureInteractor requester ) throws InteractionException;

    public void setFeatureName( String name ) {
        this.featureName = name;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public String getAspectName() {
        return this.aspectName;
    }

    public void setAspectName( String name ) {
        this.aspectName = name;
    }

    @SuppressWarnings("unchecked")
	public <V> V getAttribute( String name ) {
        return (V) this.attributes.get(name);
    }

    public String getStringAttribute( String name ) {
        return String.valueOf( this.getAttribute(name) );
    }

    public Integer getIntegerAttribute( String name ) {
        return Integer.valueOf( this.getStringAttribute( name ) );
    }

    public Double getDoubleAttribute( String name ) {
        return Double.valueOf( this.getStringAttribute( name ) );
    }

    @SuppressWarnings("unchecked")
	public <K, V> Map<K, V> getMapAttribute( String name ) {
    	return (Map<K, V>) this.getAttribute(name);
    }

    @SuppressWarnings("unchecked")
	public <V> Collection<V> getCollectionAttribute( String name ) {
        return (Collection<V>) this.getAttribute(name);
    }

    public boolean isMapAttribute( String name ) {
        return Map.class.isAssignableFrom( this.getAttribute(name).getClass() );
    }

    public boolean isIntegerAttribute( String name ) {
        return Integer.class.isAssignableFrom( this.getAttribute(name).getClass() );
    }

    public boolean isStringAttribute( String name ) {
        return String.class.isAssignableFrom( this.getAttribute(name).getClass() );
    }

    public boolean isCollectionAttribute( String name ) {
        return Collection.class.isAssignableFrom( this.getAttribute(name).getClass() );
    }

    public void setAttribute( String name, Object value ) {
        this.attributes.put( name, value );
    }

    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void setRequest( IRequest request ) {
        this.request = request;
    }

    public IRequest getRequest() {
        return this.request;
    }
    
    public IInteractionResult createResultObject() {
        return new InteractionResult();
    }

    public boolean isValid() {
        return true;
    }
}
