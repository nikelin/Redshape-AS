package com.redshape.net.fetchers;

import com.redshape.net.fetchers.charset.ICharsetConverter;
import com.redshape.utils.IEnum;
import com.redshape.utils.IFilter;
import com.redshape.utils.IParametrized;
import org.w3c.dom.Document;

import java.io.IOException;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: Nov 9, 2010
 * Time: 4:15:54 PM
 * To change this template use File | Settings | File Templates.
 */
public interface IFetcher<T extends IEnum<?>> extends IParametrized<T> {

    /**
     * Set connection timeout
     * @param timeout
     */
    public void setTimeout(int timeout);

    /**
     * Applies URLs filter for current fetcher
     * @param filter
     */
    public void setFilter(IFilter<Object> filter);

    /**
     * Fetch document on given address as raw data
     * @throws java.io.IOException
     * @return String
     */
    public String fetch(String address) throws IOException, FetcherException;

    public IResponse post(String address, Map<String, String> params) throws FetcherException;

    /**
     * Fetch document on given address as DOM-tree
     * @param address
     * @return
     * @throws java.io.IOException
     */
    public Document fetchDocument(String address) throws IOException, FetcherException;

    /**
     * Charset converting handler
     * @param converter
     */
    public void setCharsetConverter(ICharsetConverter<?> converter);


}
