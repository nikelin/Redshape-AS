package com.redshape.utils.config;

import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import java.io.*;

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
			this.file = this.getXmlHelper().getLoader().loadFile(filePath);
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

	public XMLConfig( XMLHelper helper, File file) throws ConfigException {
		this.xmlHelper = helper;
		this.file = file;
		this.init();
	}

	public XMLConfig(File file) throws ConfigException {
		super(file);
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
			this.init( this, this.getXmlHelper().buildDocument(this.file).getDocumentElement() );
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

}