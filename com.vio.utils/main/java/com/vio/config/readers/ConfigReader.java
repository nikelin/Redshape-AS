package com.vio.config.readers;

import java.util.List;
import java.util.Map;

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
     * Read data and return list presentation
     *
     * @param path
     * @return
     * @throws ConfigReaderException
     */
    public List<String> readList( String path ) throws ConfigReaderException;

    /**
     * Return names of nodes which result of given expression
     *
     * @param path
     * @return
     * @throws ConfigReaderException
     */
    public List<String> readNames( String path ) throws ConfigReaderException;

    /**
     * Reads first path and second path using readList(path) method and create Map<String, String>
     * where key is first path and value is second path
     * @param keyPath
     * @param valuePath
     * @return
     * @throws ConfigReaderException
     */
    public Map<String, String> readMap( String keyPath, String valuePath ) throws ConfigReaderException;
}

