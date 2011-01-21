package com.redshape.config;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.redshape.utils.helpers.XMLHelper;

import java.io.*;
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
    private XMLHelper xmlHelper;
    private Element node;
    private boolean isWritable;

    public XMLConfig( XMLHelper helper, String file ) throws ConfigException {
        this.xmlHelper = helper;

        this.init(file);
    }

    public XMLConfig( XMLHelper helper, File file ) throws ConfigException {
        this.xmlHelper = helper;

        this.init(file);
    }

    protected XMLConfig( Element element ) {
        this.init(element);
    }

    /**
         * Yep, this methods is really duplicates of class constructors prototypes, but it's only because
         * they helps to make surviving of logic in a spring environment of IoC.
         */
    private void init( String data ) throws ConfigException {
        this.node = this.buildDocument(data).getDocumentElement();
    }

    private void init( File file ) throws ConfigException {
        log.info( file.getPath() + ": " + Boolean.toString( file.exists() ) );
        this.node = this.buildDocument(file).getDocumentElement();
    }

    private void init( Element node ) {
        this.node = node;
    }

    public void setXmlHelper( XMLHelper helper ) {
        this.xmlHelper = helper;
    }

    public XMLHelper getXmlHelper() {
        return this.xmlHelper;
    }

    public boolean isWritable() {
        return this.isWritable;
    }

    public void makeWritable( boolean value ) {
        this.isWritable = value;
    }

    public IConfig parent() throws ConfigException {
        return new XMLConfig( (Element) this.node.getParentNode() );
    }

    public void set( String value ) throws ConfigException {
        assert !this.isNull() && this.isWritable();

        this.node.setTextContent( value );
        this.node.setNodeValue( value );
    }

    public IConfig get( String name ) throws ConfigException {
        NodeList list = this.node.getElementsByTagName(name);
        if ( list.getLength() == 0 ) {
            return new XMLConfig( (Element) null );
        } else {
            return new XMLConfig( (Element) list.item(0) );
        }
    }

    public void attribute( String name, String value ) {
        assert !this.isNull() && this.isWritable();
        
        this.node.setAttribute(name, value);
    }

    public IConfig createChild( String name ) {
        assert !this.isNull() && this.isWritable();

        Element child;
        this.node.appendChild( child = this.node.getOwnerDocument().createElement( name ) );

        return new XMLConfig( child  );
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
        return !this.isNull() && this.node.hasChildNodes();
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
        if ( this.node == null || !this.node.hasChildNodes() ) {
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

    protected Document buildDocument( String file ) throws ConfigException {
        try {
            return this.getXmlHelper().buildDocument(file);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            e.printStackTrace();
            throw new ConfigException("Cannot read from: " + file);
        }
    }

    protected Document buildDocument( File file ) throws ConfigException {
        try {
            return this.getXmlHelper().buildDocument(file);
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ConfigException();
        }
    }

    public void remove() throws ConfigException {
        if ( !this.isWritable() ) {
            throw new ConfigException("Cannot remove node in context of read-only config object");
        }

        this.node.getOwnerDocument().removeChild( this.node );
    }

    private String _value( Node node ) {
        return node.getTextContent();
    }

    @Override
    public String toString() {
        return this.value();
    }

    @Override
    public String serialize() throws ConfigException {
        try {
            return this.getXmlHelper().parseToXml( this.toDomDocument() );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new ConfigException( e.getMessage() );
        }
    }

    public Document toDomDocument() {
        assert !this.isNull();
        
        return this.node.getOwnerDocument();
    }

    public static void writeConfig( File file, XMLConfig config ) throws IOException, ConfigException {
        BufferedWriter writer = new BufferedWriter( new OutputStreamWriter( new FileOutputStream(file) ) );
        writer.write( config.serialize() );
        writer.close();
    }
}