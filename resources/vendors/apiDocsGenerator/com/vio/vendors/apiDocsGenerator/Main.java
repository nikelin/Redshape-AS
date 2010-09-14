package com.redshape.vendors.apiDocsGenerator;

import com.redshape.api.ActionInterface;
import com.redshape.api.InterfacesRegistry;
import com.redshape.plugins.Plugin;
import com.redshape.plugins.PluginInfo;

import com.redshape.utils.ResourcesLoader;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Properties;

public class Main extends Plugin {
    private static final Logger log = Logger.getLogger(Main.class);
    private static Properties properties;
    private static DocumentBuilder builder;

    static {
        try {
            Main.builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        } catch ( Throwable e ) {
            log.error("Cannot builder Document object...");
        }
    }

    public Main( String systemId, PluginInfo info ) {
        super( systemId, info );

        try {
            DOMSource source = new DOMSource( this.buildDom(InterfacesRegistry.getInterfaces()) );
            StreamSource template = new StreamSource( this.getLoader().loadFile("templates/index.xsl") );
            StreamResult result = new StreamResult( this.getProperty("out") );

            TransformerFactory.newInstance( "org.apache.xalan.processor.TransformerFactoryImpl", ClassLoader.getSystemClassLoader() )
                              .newTransformer( template )
                              .transform(source, result);
            
        } catch ( Throwable e ) {
            log.error( e.getMessage(), e );
            log.info("Generation exception");
        }
    }

    protected ResourcesLoader getLoader() {
        return com.redshape.Main.getResourcesLoader();
    }

    protected static DocumentBuilder getBuilder() {
        return builder;
    }

    protected Node buildDom( Collection<ActionInterface> interfaces ) throws Throwable {
        Document doc = getBuilder().newDocument();
        
        Node rootNode = doc.createElement("interfaces");
        doc.appendChild(rootNode);

        for ( ActionInterface iInstance : interfaces ) {
            this.buildInterfaceNode( iInstance, rootNode ) ;
        }

        return rootNode;
    }

    private Node buildInterfaceNode( ActionInterface instance, Node parent ) {
        Element iNode = parent.getOwnerDocument().createElement("interface");
        parent.appendChild( iNode );

        InvokationController iAnnotation = instance.getClass().getAnnotation( InvokationController.class );
        if ( iAnnotation == null ) {
            return null;
        }

        iNode.setAttribute( "name", iAnnotation.name() );

        Element actions = iNode.getOwnerDocument().createElement("actions");
        iNode.appendChild( actions );
        
        for ( Method m : instance.getClass().getMethods() ) {
            this.buildActionNode( m, actions );    
        }

        return iNode;
    }

    private Node buildActionNode( Method action, Node parent ) {
        InvokeHandler handler = action.getAnnotation( InvokeHandler.class );
        if ( handler == null ) {
            return null;
        }

        Element actionNode = parent.getOwnerDocument().createElement("action");
        parent.appendChild(actionNode);
        
        actionNode.setAttribute("name", handler.name() );

        Node requestNode = parent.getOwnerDocument().createElement("request");
        actionNode.appendChild( requestNode );

        ParametersList parameters = action.getAnnotation( ParametersList.class );
        if ( parameters != null ) {
            Node parametersNode = parent.getOwnerDocument().createElement("parameters");
            requestNode.appendChild(parametersNode);

            if ( !parameters.root().isEmpty() ) {
                Element rootParameter = parent.getOwnerDocument().createElement("root");
                parametersNode.appendChild( rootParameter );

                rootParameter.setNodeValue( parameters.root() );
            }

            for ( Parameter param : parameters.params() ) {
                Element parameterNode = parent.getOwnerDocument().createElement("parameter");
                parametersNode.appendChild(parameterNode);

                parameterNode.setAttribute("name", param.name() );
                parameterNode.setAttribute("type", param.type().getCanonicalName() );
                parameterNode.setAttribute("optionality", param.optionality() ? "Yes" : "No");
            }
        }

        Node permissions = parent.getOwnerDocument().createElement("permissions");
        requestNode.appendChild(permissions);

        for ( InvokePermission p : handler.permissions() ) {
            Element permissionNode = parent.getOwnerDocument().createElement("permission");
            permissions.appendChild( permissionNode );
            
            permissionNode.setAttribute( "name", p.name().name() );
        }

        ResponseDefinition responseDefinition = action.getAnnotation( ResponseDefinition.class );
        if ( responseDefinition != null ) {
            Node response = parent.getOwnerDocument().createElement("response");
            actionNode.appendChild(response);

            Node parametersNode = parent.getOwnerDocument().createElement("parameters");
            response.appendChild( parametersNode );
            
            for ( Parameter param : responseDefinition.params() ) {
                Element parameter = parent.getOwnerDocument().createElement("parameter");
                parametersNode.appendChild( parameter );

                parameter.setAttribute("name", param.name() );
                parameter.setAttribute("type", param.type().getCanonicalName() );
                parameter.setAttribute("optionality", param.optionality() ? "Yes" : "No" );
            }

            Node errors = parent.getOwnerDocument().createElement("errors");
            response.appendChild(errors);
            
            for ( ResponseError error : responseDefinition.errors() ) {
                Element errorNode = parent.getOwnerDocument().createElement("error");
                errors.appendChild( errorNode );

                errorNode.setNodeValue( error.code().name() );
            }
        }

        return actionNode;
    }

    public static String getProperty( String name ) {
        return getProperties().getProperty(name);
    }

    protected static Properties getProperties() {
        try {
            if ( properties == null ) {
                properties = new Properties();
                properties.load( new FileReader( com.redshape.Main.getResourcesLoader().loadFile("config.properties") ) );
            }

            return properties;
        } catch ( Throwable e ) {
            return null;
        }
    }

    public void init() {
        log.info("I'm initialized!");
    }

    public void unload() {
        log.info("I'm uninitialized!");
    }
}