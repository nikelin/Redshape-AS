package com.redshape.features;

import com.redshape.utils.config.IConfig;
import com.redshape.io.protocols.core.request.IRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 5:45:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFeatureAspect<T extends IFeatureInteractor> implements IFeatureAspect<T> {
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

    public IInteractionResult interact( T requester ) throws InteractionException {
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
        } catch ( Exception e ) {
            log.error( e.getMessage(), e );
            throw new InteractionException(e.getMessage(), e);
        } catch ( Throwable e ) {
            log.error(e.getMessage(), e);
            throw new InteractionException();
        }
    }

    abstract protected IInteractionResult processInteraction( T requester ) throws InteractionException;

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

    public Object getAttribute( String name ) {
        return this.attributes.get(name);
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

    public Map getMapAttribute( String name ) {
        final Map attribute = (Map) this.getAttribute(name);
        return attribute;
    }

    public <T> Collection<T> getCollectionAttribute( String name ) {
        return (Collection<T>) this.getAttribute(name);
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
