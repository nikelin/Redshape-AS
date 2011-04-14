package com.redshape.utils.config;

import java.io.Serializable;

/**
* XMLConfig handler
*
* @author nikelin
*/
public interface IConfig extends Serializable {

    /**
    * Neither node exists or not
    * @return
    */
    public boolean isNull();

    /**
    * Get child context
    * @param name
    * @return
    */
    public IConfig get( String name ) throws ConfigException;

    /**
    * Get ancestors of current node
    *
    * @return T[]
    */
    public <T extends IConfig> T[] childs();

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
    * @return
    */
    public String[] list( String name );

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
    public String attribute( String name );

    /**
    * Get value of current node
    * @return String
    */
    public String value();

    public IConfig parent() throws ConfigException;

    // @todo: Move to serializer
    public String serialize() throws ConfigException;
    
	public <V> V getRawElement();

}