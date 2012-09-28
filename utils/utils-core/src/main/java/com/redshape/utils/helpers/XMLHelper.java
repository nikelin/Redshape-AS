package com.redshape.utils.helpers;

import com.redshape.utils.ResourcesLoader;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

/**
 * @author nikelin
 */
public final class XMLHelper {
    private static final Logger log = Logger.getLogger( XMLHelper.class );

    private static TransformerFactory transformersFactory = TransformerFactory.newInstance();
    private static Transformer transformer;
    static {
        try {
            transformer = transformersFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
        }
    }

    public ResourcesLoader loader;

    public XMLHelper() {

    }

    public XMLHelper( ResourcesLoader loader ) {
        this.loader = loader;
    }

    public void setLoader( ResourcesLoader loader ) {
        this.loader = loader;
    }

    public ResourcesLoader getLoader() {
        return this.loader;
    }

    public String getNamespace( Node node ) {
        String[] nameParts = node.getNodeName().split(":");
        if ( nameParts.length > 1 ) {
            return nameParts[1];
        }

        if ( node.getParentNode() != null ) {
            return getNamespace( node.getParentNode() );
        }

        return null;
    }

    public String getSchemaLocation( String namespace, Node node ) {
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

    public Document buildDocument( String path ) throws IOException,  SAXException,  ParserConfigurationException {
        return buildDocument( this.getLoader().loadResource(path) );
    }

    public Document buildDocumentByData( String data ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument( data );
    }

    public Document buildDocument( Reader reader ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument( reader );
    }

    public Document buildDocument( InputStream stream ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument( stream );
    }

    public Document buildDocument( File file ) throws IOException, SAXException, ParserConfigurationException {
        return _buildDocument(  file );
    }

    public Document buildEmptyDocument() throws ParserConfigurationException {
        return createBuilder().newDocument();
    }

    public String parseToXml( Document document ) throws TransformerException {
        return parseToXml( document.getDocumentElement() );
    }

    public String parseToXml( Node node ) throws TransformerException {
        if ( node == null ) {
            throw new IllegalArgumentException("<null>");
        }

        StreamResult result = new StreamResult(new StringWriter());
        DOMSource source = new DOMSource(node);

        transformer.transform(source, result);

        return result.getWriter().toString();
    }

    protected DocumentBuilderFactory createFactory() {
        return DocumentBuilderFactory.newInstance();
    }

    protected DocumentBuilder createBuilder() throws ParserConfigurationException {
        DocumentBuilder builder = createFactory().newDocumentBuilder();
        builder.setErrorHandler(null);

        return builder;
    }

    protected Document _buildDocument( Object source ) throws IOException, SAXException, ParserConfigurationException {
        if ( source == null ) {
            throw new IllegalArgumentException("Source must not be null");
        }

        DocumentBuilder builder = createBuilder();

        Document doc = null;
        if ( File.class.isAssignableFrom( source.getClass() ) ) {
            doc = builder.parse( (File) source );
        } else if ( InputStream.class.isAssignableFrom( source.getClass() ) ) {
            doc = builder.parse( (InputStream) source );
        } else if ( InputStreamReader.class.isAssignableFrom( source.getClass() ) ) {
            doc = builder.parse( new InputSource( (Reader) source ) );
        } else if ( String.class.isAssignableFrom( source.getClass()) ) {
            doc = builder.parse( new InputSource( new StringReader( (String) source ) ) );
        } else {
            throw new ParserConfigurationException("Unknown source");
        }

        return doc;


    }

}
