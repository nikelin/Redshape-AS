package com.redshape.io.net.discovery.nmap.analyzers;

import com.redshape.io.*;
import com.redshape.utils.StringUtils;
import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.*;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashSet;


/**
 * NMap discovery tool output analyzer
 *
 * @author nikelin
 */
public class NMapOutputAnalyzer {
    private static final Logger log = Logger.getLogger( NMapOutputAnalyzer.class );

    private static XPath xpath = XPathFactory.newInstance().newXPath();

    private static XPathExpression PORTS_EXPRESSION;
    private static XPathExpression HOSTS_EXPRESSION;
    private static XPathExpression OS_CLASS_EXPRESSION;
    private static XPathExpression OS_MATCH_EXPRESSION;
    private static XPathExpression DISTANCE_EXPRESSION;
    private static XPathExpression ADDRESS_EXPRESSION;

    /**
         *  XPath expressions initialization
         */
    static {
        try {
            DISTANCE_EXPRESSION = xpath.compile("//distance/@value");
            PORTS_EXPRESSION = xpath.compile("//ports/port[child::state[@state='open']]");
            ADDRESS_EXPRESSION = xpath.compile("//address[@addrtype='ipv4']/@addr");
            HOSTS_EXPRESSION = xpath.compile("//host[child::status[not( @state = 'down' ) ]]");
            OS_CLASS_EXPRESSION = xpath.compile("//os/osclass[position() = last()]");
            OS_MATCH_EXPRESSION = xpath.compile("//os/osmatch[position() = last()]");
        } catch ( XPathExpressionException e ) {
            log.error( e.getMessage(), e );
            throw new RuntimeException("NMap Analyzer internal exception");
        }
    }

    private XMLHelper xmlHelper;

    public void setXmlHelper( XMLHelper helper ) {
        this.xmlHelper = helper;
    }

    public XMLHelper getXmlHelper() {
        return this.xmlHelper;
    }

    public Collection<INetworkNode> analyze( String data ) throws NMapAnalyzerException {
        try {
            return this.analyze( this.getXmlHelper().buildDocumentByData(data) );
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new NMapAnalyzerException( e.getMessage() );
        }
    }

    public Collection<INetworkNode> analyze( Document document ) throws NMapAnalyzerException {
        try {
            Collection<INetworkNode> result = new HashSet<INetworkNode>();

            Element rootNode = document.getDocumentElement();
            NodeList list = (NodeList) NMapOutputAnalyzer.HOSTS_EXPRESSION.evaluate( rootNode, XPathConstants.NODESET );
            for ( int num = 0; num < list.getLength(); num++ ) {
                INetworkNode node = this.processHostNode( (Element) list.item(num));
                if ( node != null ) {
                    result.add( node );
                }
            }

            return result;
        } catch ( XPathExpressionException e ) {
            log.error( e.getMessage(), e );
            throw new NMapAnalyzerException( e.getMessage() );
        }
    }

    protected INetworkNode processHostNode( Element node ) throws NMapAnalyzerException {
        try {
            NetworkNode result = new NetworkNode();
            NetworkNodeOS nodeOS = this.processOSNodes( node );
            if ( nodeOS.isNull() ) {
                return null;
            }

            result.setOS( nodeOS  );
            result.setPlatformType( PlatformType.detectFamily(nodeOS.getName()) );

            NodeList portsNodes = (NodeList) NMapOutputAnalyzer.PORTS_EXPRESSION.evaluate( node, XPathConstants.NODESET );
            for ( int num = 0; num < portsNodes.getLength(); num++ ) {
                result.addPort( this.processPortNode( (Element) portsNodes.item(num) ) );
            }

            result.setNetworkPoint(
                    InetAddress.getByAddress(
                        StringUtils.stringToIP(
                                (String) NMapOutputAnalyzer.ADDRESS_EXPRESSION.evaluate(node, XPathConstants.STRING)
                        )
                    )
            );


            result.setLatency( this.getLatencyInfo(node) );
            result.setDistance( Integer.parseInt( (String) NMapOutputAnalyzer.DISTANCE_EXPRESSION.evaluate( node, XPathConstants.STRING ) ) );

            return result;
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            throw new NMapAnalyzerException( e.getMessage() );
        }
    }


    protected int getLatencyInfo( Element node ) {
        return Integer.parseInt( node.getAttributeNode("endtime").getValue() ) - Integer.parseInt( node.getAttributeNode("starttime").getValue() );
    }

    protected NetworkNodePort processPortNode( Element node ) throws NMapAnalyzerException {
        NetworkNodePort port = new NetworkNodePort( Integer.parseInt( node.getAttributeNode("portid").getValue() ) );
        port.setProtocol( node.getAttributeNode("protocol").getValue() );
        port.setService( node.getChildNodes().item(1).getAttributes().getNamedItem("name").getNodeValue() );
        port.setState( node.getChildNodes().item(0).getAttributes().getNamedItem("state").equals("open") );

        return port;
    }

    protected NetworkNodeOS processOSNodes( Element node ) throws NMapAnalyzerException {
        try {
            NetworkNodeOS nodeOS = new NetworkNodeOS();

            Node osClass = (Node) NMapOutputAnalyzer.OS_CLASS_EXPRESSION.evaluate( node, XPathConstants.NODE );
            if ( osClass == null ) {
                nodeOS.markNull(true);
                return nodeOS;
            }

            nodeOS.setFamily( osClass.getAttributes().getNamedItem("osfamily").getNodeValue() );
            nodeOS.setGeneration( osClass.getAttributes().getNamedItem("osgen").getNodeValue() );

            Node osSpecification = (Node) NMapOutputAnalyzer.OS_MATCH_EXPRESSION.evaluate( node, XPathConstants.NODE );
            //@fixme: sometimes crashes with NPE
            nodeOS.setName( osSpecification.getAttributes().getNamedItem("name").getNodeValue() );

            return nodeOS;
        } catch ( XPathExpressionException e ) {
            log.error( e.getMessage(), e );
            throw new NMapAnalyzerException( e.getMessage() );
        }
    }


}
