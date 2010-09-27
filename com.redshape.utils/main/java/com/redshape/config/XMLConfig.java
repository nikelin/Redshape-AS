package com.redshape.config;

import com.redshape.config.cache.IConfigCacheProvider;
import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.util.Vector;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Sep 10, 2010
 * Time: 12:47:45 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLConfig implements IConfig {
    private static final Logger log = Logger.getLogger( XMLConfig.class );
    public static String BOOTSTRAP_CONFIG_PATH = "configs/common/bootstrap.cfg.xml";

    private Element node;
    private IConfigCacheProvider cacheProvider;

    public XMLConfig( String data ) throws ConfigException {
        this( buildDocument(data).getDocumentElement() );
    }

    public XMLConfig( File file ) throws ConfigException {
        this( buildDocument(file).getDocumentElement() );
    }

    protected XMLConfig( Element element ) {
        this.node = element;
    }

    public void setCacheProvider( IConfigCacheProvider cacheProvider ) {
        this.cacheProvider = cacheProvider;
    }

    public IConfig get( String name ) throws ConfigException {
        return new XMLConfig( (Element) this.node.getElementsByTagName(name).item(0) );
    }

    public String attribute( String name ) {
        return this.node.getAttribute(name);
    }

    public String value() {
        return this._value( this.node );
    }

    public boolean isNull() {
        return this.node == null;
    }

    public boolean hasChilds() {
        return this.node.hasChildNodes();
    }

    public String[] names() {
        if ( !this.node.hasChildNodes() ) {
            return new String[] {};
        }

        NodeList nodes = this.node.getChildNodes();
        Vector<String> names = new Vector<String>();
        for ( int i = 0; i < nodes.getLength(); i++ ) {
            if ( nodes.item(i).getNodeType() == Node.ELEMENT_NODE ) {
                names.add( nodes.item(i).getNodeName() );
            }
        }

        return names.toArray( new String[names.size()] );
    }

    public String name() {
        return this.node.getNodeName();
    }

    public <T extends IConfig> T[] childs() {
        if ( !this.hasChilds() ) {
            return (T[]) new XMLConfig[] {};
        }

        Vector<T> result = new Vector<T>();
        NodeList nodes =this.node.getChildNodes();
        for ( int i = 0; i < nodes.getLength(); i++ ) {
            if ( nodes.item(i).getNodeType() == Node.ELEMENT_NODE ) {
                result.add( (T) new XMLConfig( (Element) nodes.item(i) ) );
            }
        }

        return result.toArray( (T[]) new IConfig[ result.size() ] );
    }

    public String[] list() {
        return this.list(null);
    }

    public String[] list( String name ) {
        if ( !this.node.hasChildNodes() ) {
            return new String[] {};
        }

        NodeList nodes = this.node.getChildNodes();
        Vector<String> values = new Vector<String>();
        for ( int i = 0; i < nodes.getLength(); i++ ) {
            if ( nodes.item(i).getNodeType() == Node.ELEMENT_NODE &&
                    ( name == null || nodes.item(i).getNodeName().equals(name) ) ) {
                values.add( this._value( nodes.item(i) ) );
            }
        }

        return values.toArray( new String[values.size()] );
    }

    protected static Document buildDocument( String file ) throws ConfigException {
        try {
            return XMLHelper.buildDocument(file);
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ConfigException();
        }
    }

    protected static Document buildDocument( File file ) throws ConfigException {
        try {
            return XMLHelper.buildDocument(file);
        } catch ( Throwable e ) {
            throw new ConfigException();
        }
    }

    private String _value( Node node ) {
        return node.getTextContent();
    }

    @Override
    public String toString() {
        return this.value();
    }

}
