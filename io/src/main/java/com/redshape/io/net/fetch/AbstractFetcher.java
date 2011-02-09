package com.redshape.io.net.fetch;

import java.util.HashMap;
import java.util.Map;

import com.redshape.io.net.fetch.charset.ICharsetConverter;
import com.redshape.utils.IFilter;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 9, 2010
 * Time: 4:22:55 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractFetcher<T> implements IFetcher<T> {
    public static int DEFAULT_TIMEOUT = 30000;

    private ICharsetConverter<?> converter;
    private int timeout;
    private IFilter<Object> filter;
    private Map<T, Object> attributes = new HashMap<T, Object>();

    public AbstractFetcher() {
        this(DEFAULT_TIMEOUT);
    }

    public AbstractFetcher( int timeout ) {
        this.timeout = timeout;
    }

    public Boolean getBoolean( T name ) {
        return (Boolean) this.getAttribute(name);
    }

    public Number getNumber( T name ) {
        return (Number) this.getAttribute(name);
    }

    public String getString( T name ) {
        return String.valueOf( this.getAttribute(name) );
    }

    @Override
    public boolean hasAttribute( T name ) {
        return this.attributes.containsKey(name);
    }

    @Override
    public void setAttribute( T name, Object value  ) {
        this.attributes.put(name, value);
    }

    @Override
    public Object getAttribute( T name ) {
        return this.attributes.get(name);
    }

    @Override
    public Map<T, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public void setTimeout( int timeout ) {
        this.timeout = timeout;
    }

    protected int getTimeout() {
        return this.timeout;
    }

    @Override
    public void setFilter( IFilter<Object> filter ) {
        this.filter = filter;
    }

    public IFilter<Object> getFilter() {
        return this.filter;
    }

    @Override
    public void setCharsetConverter( ICharsetConverter<?> converter ) {
        this.converter = converter;
    }

    protected ICharsetConverter<?> getCharsetConverter() {
        return this.converter;
    }

}
