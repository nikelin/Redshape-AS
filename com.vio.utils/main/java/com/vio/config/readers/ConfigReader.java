package com.vio.config.readers;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 10:41:45 AM
 * To change this template use File | Settings | File Templates.
 */
public interface ConfigReader {

    public void initialize() throws ConfigReaderException;

    /**
     * Read single value of result
     * @param path
     * @return
     * @throws ConfigReaderException
     */
    public String read( String path ) throws ConfigReaderException;

    /**
     * Read data in list presentation
     *
     * @param path
     * @return
     * @throws ConfigReaderException
     */
    public List<String> readList( String path ) throws ConfigReaderException;

    /**
     * Return names of nodes which result of expression evaluation
     *
     * @param path
     * @return
     * @throws ConfigReaderException
     */
    public List<String> readNames( String path ) throws ConfigReaderException;
}

