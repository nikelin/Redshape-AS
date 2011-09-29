package com.redshape.utils.config;

import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.w3c.dom.*;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA. User: nikelin Date: Sep 10, 2010 Time:
 * 12:47:45 AM To change this template use File | Settings | File Templates.
 */
public class XMLConfig implements IWritableConfig {

    private static final Logger log = Logger.getLogger(XMLConfig.class);
    private XMLHelper xmlHelper;
    private Element node;
    private boolean isWritable;

    protected XMLConfig(String root, XMLHelper helper) throws ConfigException {
        this.xmlHelper = helper;

        this.initEmpty(root);
    }

    public XMLConfig(XMLHelper helper, String file) throws ConfigException {
        this.xmlHelper = helper;

        this.init(file);
    }

    public XMLConfig(XMLHelper helper, File file) throws ConfigException {
        this.xmlHelper = helper;

        this.init(file);
    }

    public XMLConfig(XMLHelper helper, InputStream stream) throws ConfigException {
        this.xmlHelper = helper;

        this.init(stream);
    }

    protected XMLConfig(Element element) {
        this.init(element);
    }

    private void initEmpty(String rootNode) throws ConfigException {
        try {
            Document doc = this.getXmlHelper().buildEmptyDocument();
            this.node = doc.createElement(rootNode);
            doc.appendChild(this.node);
        } catch (ParserConfigurationException e) {
            throw new ConfigException("Document building exception", e);
        }
    }

    /**
         * Yep, this methods is really duplicates of class constructors prototypes, but it's only because
         * they helps to make surviving of logic in a spring environment of IoC.
         */
    private void init(String data) throws ConfigException {
        this.node = this.buildDocument(data).getDocumentElement();
    }

    private void init(InputStream data) throws ConfigException {
        this.node = this.buildDocument(data).getDocumentElement();
    }

    private void init(File file) throws ConfigException {
        log.info(file.getPath() + ": " + Boolean.toString(file.exists()));
        this.node = this.buildDocument(file).getDocumentElement();
    }

    private void init(Element node) {
        this.node = node;
    }

    public void setXmlHelper(XMLHelper helper) {
        this.xmlHelper = helper;
    }

    public XMLHelper getXmlHelper() {
        return this.xmlHelper;
    }

    /* (non-Javadoc)
	 * @see com.api.commons.config.IWritableConfig#isWritable()
	 */
    @Override
    public boolean isWritable() {
        return this.isWritable;
    }

    /* (non-Javadoc)
	 * @see com.api.commons.config.IWritableConfig#makeWritable(boolean)
	 */
    @Override
    public IWritableConfig makeWritable(boolean value) {
        this.isWritable = value;
        return this;
    }

    @Override
    public IConfig parent() throws ConfigException {
        return new XMLConfig((Element) this.node.getParentNode());
    }

    /* (non-Javadoc)
	 * @see com.api.commons.config.IWritableConfig#set(java.lang.String)
	 */
    @Override
    public IWritableConfig set(String value) throws ConfigException {
        this.node.setTextContent(value);
        this.node.setNodeValue(value);

        return this;
    }

    @Override
    public IConfig get(String name) throws ConfigException {
        if (this.isNull()) {
            return this.createNull();
        }

		for ( IConfig config : this.childs() ) {
			if ( config.name().equals(name) ) {
				return config;
			}
		}

		return this.createNull();
    }

    @Override
    public String[] attributeNames() {
        NamedNodeMap attributes = this.node.getAttributes();
        String[] names = new String[attributes.getLength()];
        for (int i = 0; i < attributes.getLength(); i++) {
            names[i] = attributes.item(i).getNodeName();
        }

        return names;
    }

    @Override
    public IWritableConfig attribute(String name, String value) {
        this.node.setAttribute(name, value);
        return this;
    }

    @Override
    public IWritableConfig createChild(String name) {
        Element child;
        this.node.appendChild(child = this.node.getOwnerDocument().createElement(name));

        return new XMLConfig(child);
    }

    @Override
    public String attribute(String name) {
        return this.node.getAttribute(name);
    }

    @Override
    public String value() {
        return this._value(this.node);
    }

    @Override
    public boolean isNull() {
        return this.node == null;
    }

    @Override
    public boolean hasChilds() {
        return !this.isNull() && this.node.hasChildNodes();
    }

    @Override
    public String[] names() {
        if (!this.node.hasChildNodes()) {
            return new String[]{};
        }

        NodeList nodes = this.node.getChildNodes();
        List<String> names = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                names.add(nodes.item(i).getNodeName());
            }
        }

        return names.toArray(new String[names.size()]);
    }

    @Override
    public String name() {
        return this.node.getNodeName();
    }

    @SuppressWarnings({"unchecked", "unchecked"})
    @Override
    public <T extends IConfig> T[] childs() {
        if (!this.hasChilds()) {
            return (T[]) new IWritableConfig[]{};
        }

        List<T> result = new ArrayList<T>();
        NodeList nodes = this.node.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                result.add((T) new XMLConfig((Element) nodes.item(i)));
            }
        }

        return result.toArray((T[]) new IConfig[result.size()]);
    }

    @Override
    public String[] list() {
        return this.list(null);
    }

    @Override
    public String[] list(String name) {
        if (this.node == null || !this.node.hasChildNodes()) {
            return new String[]{};
        }

        NodeList nodes = this.node.getChildNodes();
        List<String> values = new ArrayList<String>();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE
                    && (name == null || nodes.item(i).getNodeName().equals(name))) {
                values.add(this._value(nodes.item(i)));
            }
        }

        return values.toArray(new String[values.size()]);
    }

    protected Document buildDocument(InputStream stream) throws ConfigException {
        try {
            return this.getXmlHelper().buildDocument(stream);
        } catch (Throwable e) {
            throw new ConfigException("Cannot read from stream", e);
        }
    }

    protected Document buildDocument(String file) throws ConfigException {
        try {
            return this.getXmlHelper().buildDocument(file);
        } catch (Throwable e) {
            throw new ConfigException("Cannot read from: " + file, e);
        }
    }

    protected Document buildDocument(File file) throws ConfigException {
        try {
            return this.getXmlHelper().buildDocument(file);
        } catch (Throwable e) {
            log.error(e.getMessage(), e);
            throw new ConfigException();
        }
    }

    @Override
    public IWritableConfig remove() throws ConfigException {
        if (!this.isWritable()) {
            throw new ConfigException("Cannot remove node in context of read-only config object");
        }

        IWritableConfig parent = (IWritableConfig) this.parent();

        this.node.getOwnerDocument().removeChild(this.node);

        return parent;
    }

    private String _value(Node node) {
        return node == null ? null : node.getTextContent();
    }

    @Override
    public String toString() {
        return this.value();
    }

    @Override
    public IWritableConfig asWritable() {
        return (IWritableConfig) this;
    }

    @Override
    public IWritableConfig append(IConfig config) {
        if (this.getRawElement() == null) {
            return this.createNull();
        }

        @SuppressWarnings("deprecation")
        Node node = this.<Element>getRawElement()
                        .getOwnerDocument()
                        .importNode(config.<Node>getRawElement(), true);

        return new XMLConfig((Element) this.<Element>getRawElement().appendChild(node));
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

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getRawElement() {
        return (V) this.node;
    }

    public Document toDomDocument() {
        assert !this.isNull();

        return this.node.getOwnerDocument();
    }

    public static XMLConfig createEmpty(String rootNode) throws ConfigException {
        return new XMLConfig(rootNode, new XMLHelper());
    }

    public static void writeConfig(File file, XMLConfig config) throws IOException, ConfigException {
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));

        String result = config.serialize();

        writer.write(result);
        writer.close();
    }

	private IWritableConfig createNull() {
		return new XMLConfig( (Element) null );
	}
}