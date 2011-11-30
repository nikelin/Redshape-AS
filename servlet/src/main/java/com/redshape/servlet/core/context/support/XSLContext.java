package com.redshape.servlet.core.context.support;

import com.redshape.servlet.core.IHttpRequest;
import com.redshape.servlet.core.IHttpResponse;
import com.redshape.servlet.core.context.IResponseContext;
import com.redshape.servlet.core.context.SupportType;
import com.redshape.servlet.core.controllers.ProcessingException;
import com.redshape.servlet.views.ILayout;
import com.redshape.servlet.views.IView;
import com.redshape.utils.Commons;
import com.redshape.utils.ResourcesLoader;
import com.redshape.utils.helpers.XMLHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerException;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.servlet.core.context.support
 * @date 11/4/11 2:53 PM
 */
public class XSLContext implements IResponseContext {
	private static final Logger log = Logger.getLogger( XSLContext.class );

	@Autowired( required = true )
	private XMLHelper helper;

	@Autowired( required = true )
	private ResourcesLoader loader;

	public ResourcesLoader getLoader() {
		return loader;
	}

	public void setLoader(ResourcesLoader loader) {
		this.loader = loader;
	}

	public XMLHelper getHelper() {
		return helper;
	}

	public void setHelper(XMLHelper helper) {
		this.helper = helper;
	}

	@Override
	public void proceedResponse(IView view, IHttpRequest request, IHttpResponse response) throws ProcessingException {
		try {
			StreamResult result = new StreamResult( new StringWriter() );
			this.proceedTransformation( view.getLayout(), view, result, true );

			response.setCharacterEncoding("UTF-8");
			response.getWriter().write( result.getWriter().toString() );
		} catch ( TransformerException e ) {
			throw new ProcessingException( "Transformer exception", e );
		} catch ( ParserConfigurationException e ) {
			throw new ProcessingException( "XML document parsing exception", e );
		} catch ( IOException e ) {
			throw new ProcessingException( "I/O related exception", e);
		} catch ( Throwable e ) {
			throw new ProcessingException( "Unknown internal exception", e );
		}
	}

	protected void proceedTransformation( ILayout layout, IView view, Result result,
										  boolean proceedNested ) throws IOException, TransformerException,
			ParserConfigurationException, SAXException {
		File viewFile = this.loadView(Commons.select(layout, view));

		javax.xml.transform.Source xmlSource =
					new DOMSource(this.convertView(layout, view, proceedNested));
		StreamSource xsltSource = new StreamSource(
			new FileInputStream(viewFile),
			viewFile.toURI().toURL().toExternalForm()
		);

		javax.xml.transform.TransformerFactory transFact =
				javax.xml.transform.TransformerFactory.newInstance(  );

		javax.xml.transform.Transformer trans =
				transFact.newTransformer(xsltSource);
		trans.setErrorListener( new ErrorListener() {
			@Override
			public void warning(TransformerException exception) throws TransformerException {
				log.error( exception.getMessage(), exception );
			}

			@Override
			public void error(TransformerException exception) throws TransformerException {
				log.error( exception.getMessage(), exception );
			}

			@Override
			public void fatalError(TransformerException exception) throws TransformerException {
				log.error( exception.getMessage(), exception );
			}
		});

		if ( trans != null && xmlSource != null ) {
			trans.transform(xmlSource, result);
		} else {
			log.error("Unable to build transformation handler!");
		}
	}

	protected Node convertView( ILayout layout, IView view, boolean proceedNested ) throws ParserConfigurationException, SAXException,
													IOException, TransformerException {
		Document doc = this.getHelper().buildEmptyDocument();

		Element documentElement = doc.createElement("view");

		Element contentNode = doc.createElement("content");

		if ( proceedNested ) {
			DOMResult result = new DOMResult();
			this.proceedTransformation( null, view, result, false );

			Document resultDoc = (Document) result.getNode();
			if ( resultDoc != null && resultDoc.getDocumentElement() != null ) {
				Node importedDocElement = doc.importNode(resultDoc.getDocumentElement(),
														true);
				contentNode.appendChild(importedDocElement);
			}
		}

		documentElement.appendChild(contentNode);

		doc.appendChild(documentElement);

		return documentElement;
	}

	protected File loadView( IView view ) throws IOException {
		return this.getLoader().loadFile( view.getScriptPath() );
	}

	@Override
	public SupportType isSupported(IView view) {
		return view.getExtension().equals("xsl") ? SupportType.SHOULD : SupportType.NO;
	}

	@Override
	public SupportType isSupported(IHttpRequest request) {
		return SupportType.MAY;
	}
}
