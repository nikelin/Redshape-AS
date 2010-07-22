package com.vio.io.protocols.vanilla.request;

import com.vio.render.Renderable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * ApiUser: nikelin
 * Date: Jan 12, 2010
 * Time: 1:38:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class InterfaceInvocation implements Renderable {

    private String id;
    private String interfaceId;
    private String action;
    private Map<String, Object> params = new HashMap<String, Object>();
    private boolean is_valid;
    private Map<String, Object> responce = new HashMap<String, Object>();

    public InterfaceInvocation() {}

    public InterfaceInvocation( String interfaceId, String action ) {
        this( null, interfaceId, action );
    }

    public InterfaceInvocation( String id, String interfaceId, String action ) {
        this( id, interfaceId, action, new HashMap<String, Object>() );
    }

    public InterfaceInvocation( String id, String interfaceId, String action, Map<String, Object> params ) {
        this( id, interfaceId, action, new HashMap<String, Object>(), new HashMap<String, Object>());
    }

    public InterfaceInvocation( String id, String interfaceId, String action, Map<String, Object> params,  Map<String, Object> responce) {
        this.id = id;
        this.interfaceId = interfaceId;
        this.action = action;
        this.params = params;
        this.responce = responce;
    }

    public InterfaceInvocation setId( String id ) {
        this.id = id;
        return this;
    }

    public String getId() {
        return this.id;
    }

    public void setAction( String action ) {
        this.action = action;
    }

    public String getAction() {
        return this.action;
    }

    public void setInterfaceId( String interfaceId ) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceId() {
        return this.interfaceId;
    }

    public boolean isValid() {
        return this.is_valid;
    }

    public void markInvalid( boolean state ) {
        this.is_valid = state;
    }

    public void addParam( String name, Object value ) {
        this.params.put( name, value );
    }

    public boolean hasParam( String name ) {
        return this.params.containsKey(name);
    }

    public Map<String, Object> getParams() {
        return this.params;
    }

    public Object getParam( String name ) {
        return this.params.get(name);
    }

    public Map<String, Object> getMap( String name ) {
        return (Map<String, Object>) this.params.get(name);
    }

    public List<Object> getList( String name ) {
        return (List<Object>) this.params.get(name);
    }

    public String getString( String name ) {
        return this.params.get(name).getClass().equals(String.class) ? (String) this.params.get(name) : String.valueOf( this.params.get(name) );
    }

    public Integer getInteger( String name ) {
        return Integer.parseInt( String.valueOf( this.params.get(name) ) );
    }

    public void setParams( Map<String, Object> params ) {
        this.params = params;
    }

    public Map<String, Object> getResponce() {
        return responce;
    }

    public void setResponce(Map<String, Object> responce) {
        this.responce = responce;
    }
}
