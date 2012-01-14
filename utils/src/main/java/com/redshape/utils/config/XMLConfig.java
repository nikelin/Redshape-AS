package com.redshape.utils.config;

import com.redshape.utils.StringUtils;
import com.redshape.utils.config.sources.FileSource;
import com.redshape.utils.config.sources.IConfigSource;
import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.Deque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * XML configuration files support
 *
 * @author nikelin
 */
public class XMLConfig extends AbstractConfig {

    private static final Logger log = Logger.getLogger(XMLConfig.class);
    private XMLHelper xmlHelper;
    private Element node;

	public XMLConfig( XMLHelper helper, String filePath ) throws ConfigException {
		try {
			this.xmlHelper = helper;
			this.source = new FileSource( this.getXmlHelper().getLoader().loadFile(filePath) );
			this.init();
		} catch ( IOException e ) {
			throw new ConfigException( e.getMessage(), e );
		}
	}

	public XMLConfig(IConfig parent, String name, String value) {
		super(parent, name, value);
	}

	public XMLConfig(String name, String value) {
		super(name, value);
	}

    @Deprecated
    public XMLConfig( XMLHelper helper, File file ) throws ConfigException {
        this(helper, new FileSource(file) );
    }
    
	public XMLConfig( XMLHelper helper, IConfigSource source) throws ConfigException {
		this.xmlHelper = helper;
        this.source = source;
		this.init();
	}

	public XMLConfig(IConfigSource source) throws ConfigException {
		super(source);
	}

	public void setXmlHelper(XMLHelper helper) {
        this.xmlHelper = helper;
    }

    public XMLHelper getXmlHelper() {
        return this.xmlHelper;
    }

	@Override
    protected void init() throws ConfigException {
		try {
			this.init( this, this.getXmlHelper().buildDocument(this.source.getReader()).getDocumentElement() );
		} catch ( Throwable e ) {
			throw new ConfigException( e.getMessage(), e );
		}
    }

	protected void init( XMLConfig config, Element element ) throws ConfigException {
		/**
		 * Initialize attributes
		 */
		NamedNodeMap attributes = element.getAttributes();
		for ( int i = 0; i < attributes.getLength(); i++ ) {
			Node attribute = attributes.item(i);
			config.attributes.put( attribute.getNodeName(), attribute.getNodeValue() );
		}

		config.set( element.getTextContent() );
		config.name = element.getNodeName();

		/**
		 * Initialize child nodes
		 */
		Node child = element.getFirstChild();
		while ( child != null ) {
			if ( child.getNodeType() == Node.ELEMENT_NODE ) {
				XMLConfig childConfig = (XMLConfig) this.createChild(child.getNodeName());
				this.init( childConfig, (Element) child );
				config.append(childConfig);
			}

			child = child.getNextSibling();
		}
	}

	@Override
	protected IConfig createNull() {
		XMLConfig config = new XMLConfig(null, null, null);
		config.nulled = true;
		return config;
	}

	@Override
    public IConfig createChild(String name) throws ConfigException {
		return new XMLConfig(this, name, null);
    }

    @Override
    public String toString() {
        return this.name();
    }

    @Override
    public String serialize() throws ConfigException {
        try {
            return this.getXmlHelper().parseToXml(this.toDomDocument());
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new ConfigException(e.getMessage());
        }
    }

    public Document toDomDocument() {
        assert !this.isNull();

        return this.node.getOwnerDocument();
    }

    public static void writeConfig(File file, XMLConfig config) throws IOException, ConfigException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        String result = config.serialize();

        writer.write(result);
        writer.close();
    }
    
    public static XMLConfig build( XMLHelper helper, String declaration ) throws ConfigException {
        try {
            XMLConfig config = new XMLConfig("config", null);
            config.init( config, helper.buildDocumentByData(declaration).getDocumentElement() );
            return config;
        } catch ( SAXException e ) {
            throw new ConfigException("XML data parsing failed", e );
        } catch ( ParserConfigurationException e ) {
            throw new ConfigException("XML data parsing failed", e );
        } catch ( IOException e ) {
            throw new ConfigException("I/O related exception", e );
        }
    }

}