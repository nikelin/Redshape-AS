package com.redshape.utils.serializing.beans;

import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.helpers.XMLHelper;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.converters.reflection.ReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.StaxDriver;
import org.w3c.dom.Document;

import java.io.File;
import java.io.InputStream;

/**
 * Abstract XStream-based beans serializer
 *
 * @author nikelin
 * @date 07/04/11
 * @package com.redshape.utils.serializing.beans
 */
public abstract class AbstractBeansSerializer {
    private XStream loader;
    private XMLHelper xmlHelper;
    private ResourcesLoader resourcesLoader;

	public XMLHelper getXmlHelper() {
    	return this.xmlHelper;
    }

    protected ResourcesLoader getResourcesLoader() {
    	return this.resourcesLoader;
    }

    public void setResourcesLoader( ResourcesLoader loader ) {
    	this.resourcesLoader = loader;
    }

    public void setXmlHelper( XMLHelper helper ) {
    	this.xmlHelper = helper;
    }

    protected XStream getLoader() {
        return this.getLoader( new PureJavaReflectionProvider(), new StaxDriver() );
    }

    protected XStream getLoader( ReflectionProvider provider, HierarchicalStreamDriver driver ) {
        if ( this.loader == null ) {
            this.loader = new XStream( provider, driver );
        }

        return this.loader;
    }

    protected Document buildDocument( InputStream stream ) throws Throwable {
        return this.getXmlHelper().buildDocument(stream);
    }

    protected Document buildDocument( File file ) throws Throwable {
        return this.getXmlHelper().buildDocument(file);
    }

}
