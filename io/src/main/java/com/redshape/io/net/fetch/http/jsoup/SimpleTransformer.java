/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.redshape.io.net.fetch.http.jsoup;

import java.io.StringWriter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.XmlDeclaration;

/**
 *
 * @author user
 */
public class SimpleTransformer {
    private static final Logger log = Logger.getLogger( SimpleTransformer.class );
    private DocumentBuilder builder;


    public SimpleTransformer() {
        try {
            builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new RuntimeException( e.getMessage(), e );
        }
    }

    public org.w3c.dom.Document transform( Document document )  {
        org.w3c.dom.Document doc = builder.newDocument();
        doc.setDocumentURI(  document.baseUri() );

        org.w3c.dom.Element rootNode = doc.createElement( "html");

        org.w3c.dom.Element headNode = (org.w3c.dom.Element) this.transformNode( doc, document.head() );
        if ( headNode != null ) {
            rootNode.appendChild( headNode );
        }

        org.w3c.dom.Element bodyNode = (org.w3c.dom.Element) this.transformNode( doc, document.body() );
        if ( bodyNode != null ) {
            rootNode.appendChild( bodyNode );
        }

        doc.appendChild(rootNode);

        return doc;
    }

    protected org.w3c.dom.Node transformNode( org.w3c.dom.Document doc, Node node ) {
        org.w3c.dom.Node domNode = null;
        if ( node instanceof TextNode ) {
            domNode = doc.createTextNode( ( (TextNode) node ).text() );
        } else if ( node instanceof XmlDeclaration ) {

        } else if ( node instanceof Element ) {
            domNode = doc.createElement( node.nodeName() );

            for ( Attribute attr : node.attributes() ) {
                org.w3c.dom.Attr attribute = doc.createAttribute( attr.getKey() );
                attribute.setValue(  attr.getValue() );

                domNode.getAttributes().setNamedItem( attribute );
            }

            for ( Node childNode : node.childNodes() ) {
                org.w3c.dom.Node childTransformedNode = this.transformNode( doc, childNode );
                if ( childTransformedNode != null ) {
                    domNode.appendChild( childTransformedNode );
                }
            }
        }

        return domNode;
    }

}
