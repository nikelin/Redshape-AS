package com.vio.config.readers;

import com.vio.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jan 14, 2010
 * Time: 10:43:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class XMLConfigReader extends AbstractReader {
    private static final Logger log = Logger.getLogger( com.vio.config.readers.XMLConfigReader.class );

    private boolean initialized;
    private Document document;
    private Element rootNode;
    private Map<String, Object> paths = new HashMap<String, Object>();
    private XPath xpath;

    public XMLConfigReader( File file ) {
        super( file );
    }

    public XMLConfigReader( InputStream stream ) {
        super( stream );
    }

    public void initialize() throws ConfigReaderException {
        if ( this.initialized ) {
            return;
        }

        try {
            if ( this.getInputStream() != null ) {
                this.document = XMLHelper.buildDocument( this.getInputStream() );
            } else {
                System.out.println( "Reading config data from file: " + this.getFile().getPath() );
                this.document = XMLHelper.buildDocument( this.getFile() );
            }

            this.rootNode = this.document.getDocumentElement();
        } catch ( Throwable e ) {
            e.printStackTrace();
            throw new ConfigReaderException("Config parsing exception");
        }

        this.xpath = XPathFactory.newInstance().newXPath();
        this.initialized = true;
    }

    public List<String> readNames( String path ) throws ConfigReaderException {
        this.initialize();

        try {
            XPathExpression expr = this.xpath.compile(path);

            List<String> result = new ArrayList<String>();

            NodeList list = (NodeList) expr.evaluate( this.document, XPathConstants.NODESET );
            for ( int item = 0; item < list.getLength(); item++ ) {
                result.add( list.item(item).getNodeName() );
            }

            return result;
        } catch ( XPathExpressionException e ) {
            throw new ConfigReaderException("Cannot read nodes names");
        }
    }

    public List<String> readList( String path ) throws ConfigReaderException {
        this.initialize();

        try {
            List<String> result = new ArrayList<String>();
            if ( !this.paths.containsKey(path) ) {
                XPathExpression expr = this.xpath.compile(path);

                NodeList list = (NodeList) expr.evaluate( this.document, XPathConstants.NODESET );
                for( int item = 0; item < list.getLength(); item++ ) {
                    Node node = list.item(item);

                    result.add( node.getNodeValue() );
                }

                this.paths.put( path, result );
            }

            result = (List<String>) this.paths.get(path);

            return result;
        } catch ( XPathExpressionException e ) {
            log.error( e.getMessage(), e );
            throw new ConfigReaderException("Wrong or non-exists path!");
        }
    }

    public String read(String path)throws ConfigReaderException {
        this.initialize();

        try {
            if ( !this.paths.containsKey(path) ) {
                this.paths.put( path, String.valueOf( this.xpath.compile(path).evaluate( this.document, XPathConstants.STRING) ) );
            }

            return (String) this.paths.get(path);
        } catch ( XPathExpressionException e ) {
            log.error( e.getMessage(), e );
            throw new ConfigReaderException("Wrong or non-exists path!");
        }
    }

}
