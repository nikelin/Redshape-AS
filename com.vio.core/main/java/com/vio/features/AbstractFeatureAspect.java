package com.vio.features;

import com.vio.features.validators.IFeatureValidator;

import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 5, 2010
 * Time: 5:45:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFeatureAspect<T extends IFeatureInteractor> implements IFeatureAspect<T> {
    private Map<String, Object> attributes = new HashMap();
    private Set<IFeatureValidator> validators = new HashSet();
    private String featureName;
    private String aspectName;

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

    public void setAttribute( String name, Object value ) {
        this.attributes.put( name, value );
    }

    public boolean hasAttribute( String name ) {
        return this.attributes.containsKey(name);
    }

    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    public void addValidator( IFeatureValidator validator ) {
        this.validators.add(validator);
    }

    public Collection<IFeatureValidator> getValidators() {
        return this.validators;
    }

    public boolean isValid() {
        for ( IFeatureValidator validator : this.getValidators() ) {
            if ( !validator.isValid(this) ) {
                return false;
            }
        }

        return true;
    }
}
