package com.redshape.utils.config;

import java.io.Serializable;
import java.util.List;

/**
 * Configurations holder interface.
 *
* @author nikelin
 */
public interface IConfig extends Serializable {

    /**
     * Neither node exists or not
     *
     * @return
     */
    public boolean isNull();

    /**
    * Get child context
    *
    * @throws ConfigException
    * @param name
    * @return
    */
    public IConfig get(String name) throws ConfigException;

    /**
    * Get ancestors of current node
    *
    * @param <T>
    * @return T[]
    */
    public <T extends IConfig> List<T> childs();

    /**
        * Doest current node has ancestors?
        * @return
        */
    public boolean hasChilds();

    /**
    * Get ancestors values
    * @return
    */
    public String[] list();

    /**
    * Get specified ancestors nodes values
    *
    * @param name
    * @return
    */
    public String[] list(String name);

    /**
        * Name of current node
        * @return
        */
    public String name();

    /**
    * Read and return all names of the children
    *
    * @return String[]
    */
    public String[] names();

    /**
    * Get named attribute value
    * @param name
    * @return String
    */
    public String attribute(String name);

    /**
     * Get name of attributes which related to
     * the current node.
     *
     * @return
     */
    public String[] attributeNames();

    /**
    * Get value of current node
    * @return String
    */
    public String value();

    /**
     * Return current config parent node
     * @return
     * @throws ConfigException
     */
    public IConfig parent() throws ConfigException;

    /**
     * Return string representation for the current node
     * @return
     * @throws ConfigException
     */
    public String serialize() throws ConfigException;

	public IConfig append( IConfig config );

	public IConfig set(String value) throws ConfigException;

	public IConfig attribute(String name, String value);

	public IConfig createChild(String name) throws ConfigException;

	public IConfig parent( IConfig config );

	public IConfig remove() throws ConfigException;

	public IConfig remove(IConfig config) throws ConfigException;

}