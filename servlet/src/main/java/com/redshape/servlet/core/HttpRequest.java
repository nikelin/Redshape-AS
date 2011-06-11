package com.redshape.servlet.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;
import org.apache.poi.util.StringUtil;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10/10/10
 * Time: 11:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequest extends HttpServletRequestWrapper implements IHttpRequest {
	@SuppressWarnings("unused")
	private static final Logger log = Logger.getLogger( HttpRequest.class );
	
	private boolean initialized;
    private String controller;
    private String action;
    private String requestData;
    private Map<String, Object> parameters = new HashMap<String, Object>();

    public HttpRequest( HttpServletRequest request ) {
        super(request);
        
        try {
        	this.initParameters();
        } catch ( IOException e ) {
        	throw new IllegalArgumentException("Unable to read parameters", e);
        }
    }
    
    protected JSONObject readJSONRequest( HttpServletRequest request )
		throws IOException {
		String requestData = this.readRequest();
		
		if ( requestData.isEmpty() ) {
			throw new IllegalArgumentException("Request is empty");
		}
		
		return this.readJSONRequest( requestData );
	}
	
	protected JSONObject readJSONRequest(String data) {
	    return JSONObject.fromObject(data);
	}
    
	protected void initParameters() throws IOException {
		if ( this.initialized ) {
			return;
		}
		
		String data = this.readRequest();
    	
    	if ( data.startsWith("{") && data.endsWith("}") ) {
    		JSONObject object = this.readJSONRequest( data );
    		for ( Object key : object.keySet() ) {
    			this.parameters.put( String.valueOf( key ), object.get(key) );
    		}
    	} else {
        	if ( this.parameters == null ) {
        		this.parameters = new HashMap<String, Object>();
        	}
        	
		    for (String param : data.split("&")) {
	            String[] paramParts = param.split("=");
	
	            String value = paramParts.length > 1 ? paramParts[1] : null;
	            
	            this.parameters.put( paramParts[0], 
	            		value != null ? StringEscapeUtils.escapeHtml( URLDecoder.decode( value, "UTF-8" ) ) : null );
	        }
    	}
    	
    	this.initialized = true;
	}
	
	@Override
    public String getParameter(String name ) {
		try {
			String data = super.getParameter(name);
			if ( data != null ) {
				return data;
			}
			
			this.initParameters();
			
			return String.valueOf( this.parameters.get(name) );
		} catch ( IOException e ) {
			throw new IllegalArgumentException( "Unable to grab parameter value", e );
		}
	}

	protected String readRequest() throws IOException {
		if ( this.requestData != null ) {
			return this.requestData;
		}
		
	    StringBuffer data = new StringBuffer();
	    String buff;
	    InputStreamReader reader = new InputStreamReader( this.getInputStream() );
	    
	    BufferedReader buffer = new BufferedReader( reader );
	    buffer.skip(0);
	    while (null != (buff = buffer.readLine())) {
	        data.append(buff);
	    }
	
	    return this.requestData = data.toString();
	}

    public boolean isPost() {
        return this.getMethod().equals("POST");
    }

    public void setController( String name ) {
        this.controller = name;
    }

    public String getController() {
        return this.controller;
    }

    public void setAction( String name ) {
        this.action = name;
    }

    public String getAction() {
        return this.action;
    }

	@Override
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
		this.initialized = true;
	}

	@Override
	public Map<String, Object> getParameters() {
		return this.parameters;
	}

}
