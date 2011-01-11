package com.redshape.utils.helpers;

import com.redshape.utils.Registry;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Feb 4, 2010
 * Time: 5:57:37 PM
 * To change this template use File | Settings | File Templates.
 */
public final class XMLHelper {
    private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    public static String getNamespace( Node node ) {
        String[] nameParts = node.getNodeName().split(":");
        if ( nameParts.length > 1 ) {
            return nameParts[1];
        }

        if ( node.getParentNode() != null ) {
            return getNamespace( node.getParentNode() );
        }

        return null;
    }

    public static String getSchemaLocation( String namespace, Node node ) {
        String location = null;

        for ( Node attr = node.getAttributes().item(0); location == null && attr != null; attr = attr.getNextSibling() ) {
            if ( attr.getNodeName().startsWith("xmlns") && attr.getNodeName().endsWith( namespace ) ) {
                location = attr.getNodeValue();
            }
        }

        if ( location == null ) {
            location = getSchemaLocation( namespace, node.getParentNode() );
        }

        return location;
    }

    public static Document buildDocument( String path ) throws IOException, SAXException, ParserConfigurationException {
        return buildDocument( Registry.getResourcesLoader().loadFile( path ) );
    }

    public static Document buildDocument( InputStream stream ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument( createBuilder(), stream );
    }

    public static Document buildDocument( File file ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument( createBuilder(), file );
    }

    protected static DocumentBuilder createBuilder() throws ParserConfigurationException {
        DocumentBuilder builder = factory.newDocumentBuilder();
        builder.setErrorHandler(null);

        return builder;
    }

    protected static Document _buildDocument( DocumentBuilder builder, Object source ) throws IOException, SAXException,  ParserConfigurationException {
        Document doc = null;
        if ( source.getClass().equals( File.class ) ) {
            doc = builder.parse( (File) source );
        } else if ( source.getClass().equals( InputStream.class ) ) {
            doc = builder.parse( (InputStream) source );
        } else {
            throw new ParserConfigurationException("Unknown source");
        }

        return doc;
    }

}