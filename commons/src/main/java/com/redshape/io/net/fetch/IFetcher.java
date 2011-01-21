package com.redshape.io.net.fetch;

import org.w3c.dom.Document;

import com.redshape.io.net.fetch.charset.ICharsetConverter;
import com.redshape.io.net.fetch.http.jsoup.HttpResponse;
import com.redshape.utils.IFilter;

import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 9, 2010
 * Time: 4:15:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFetcher<T> {

    /**
     * Set connection timeout ( ye, cap' )
     * @param timeout
     */
    public void setTimeout( int timeout );

    /**
     * Applies URLs filter for current fetcher
     * @param filter
     */
    public void setFilter( IFilter<Object> filter );

    /**
     * Set fetcher internal attribute
     */
    public void setAttribute( T name, Object value );

    /**
     * Get fetcher attribute value
     * @return
     */
    public Object getAttribute( T name );

    /**
     * Get all fetcher attributes
     * @return
     */
    public Map<T, Object> getAttributes();

    /**
     * Check that attribute exists in attributes store
     * @retur
     */
    public boolean hasAttribute( T name );

    /**
     * Fetch document on given address as raw data
     * @throws IOException
     * @return String
     */
    public String fetch( String address ) throws IOException, FetcherException;

    public HttpResponse post( String address, Map<String, String> params ) throws FetcherException;

    /**
     * Fetch document on given address as DOM-tree
     * @param address
     * @return
     * @throws IOException
     */
    public Document fetchDocument( String address ) throws IOException, FetcherException;

    /**
     * Charset converting handler
     * @param converter
     */
    public void setCharsetConverter( ICharsetConverter<?> converter );


}
