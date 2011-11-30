package com.redshape.utils.helpers;

import com.redshape.utils.ResourcesLoader;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
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
 *
 * @author nikelin
 *
 */
public final class XMLHelper {
	private static final Logger log = Logger.getLogger( XMLHelper.class );

	private static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	private static DocumentBuilder domBuilder;
	static {
		try {
			// factory.setValidating(false);
			// factory.setNamespaceAware(true);

			domBuilder = createBuilder();
			domBuilder.setErrorHandler( new ErrorHandler() {
				@Override
				public void warning(SAXParseException exception) throws SAXException {
					log.error( exception.getMessage(), exception );
				}

				@Override
				public void error(SAXParseException exception) throws SAXException {
					log.error( exception.getException(), exception );
				}

				@Override
				public void fatalError(SAXParseException exception) throws SAXException {
					log.error( exception.getMessage(), exception );
				}
			});
		} catch ( Throwable e ) {
			log.error( e.getMessage(), e );
		}
	}

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

	@Deprecated
	/**
	 * @todo: for versions compitability ( currently exists method buildDocument( String filePath ) )
	 */
	public Document buildDocumentByData( String data ) throws IOException, SAXException, ParserConfigurationException {
		return _buildDocument( domBuilder, data );
	}

	public Document buildDocument( InputStream stream ) throws IOException, SAXException, ParserConfigurationException {
		return _buildDocument( domBuilder, stream );
	}

	public Document buildDocument( File file ) throws IOException, SAXException, ParserConfigurationException {
		return _buildDocument( domBuilder, file );
	}

	public Document buildEmptyDocument() throws ParserConfigurationException {
		return domBuilder.newDocument();
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

	protected static DocumentBuilder createBuilder() throws ParserConfigurationException {
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(null);

		return builder;
	}

	@SuppressWarnings("deprecation")
	protected Document _buildDocument( DocumentBuilder builder, Object source ) throws IOException, SAXException, ParserConfigurationException {
		if ( source == null ) {
			throw new IllegalArgumentException("Source must not be null");
		}

		Document doc = null;
		if ( File.class.isAssignableFrom( source.getClass() ) ) {
			doc = builder.parse( (File) source );
		} else if ( InputStream.class.isAssignableFrom( source.getClass() ) ) {
			doc = builder.parse( (InputStream) source );
		} else if ( String.class.isAssignableFrom( source.getClass()) ) {
			log.info( source );
			doc = builder.parse( new StringBufferInputStream( (String) source ) );
		} else {
			throw new ParserConfigurationException("Unknown source");
		}

		return doc;
	}

}
