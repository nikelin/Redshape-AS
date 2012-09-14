package com.redshape.persistence.dao.serialization.visitors;

import java.net.MalformedURLException;
import java.net.URL;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

public class URLSerializer extends AbstractSerializerVisitor<URL> {

	@Override
    public byte[] serialize(URL object) {
	    if (object == null || !(object instanceof URL))
	    	return null;
	    return object.toString().getBytes();
    }

	@Override
    public URL deserialize(byte[] value) {
	    String val = new String(value);
	    try {
	        return new URL(val);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL in database found, cannot deserialize", e);
        }
    }

	@Override
    public Class<?> getEntityType() {
	    return URL.class;
    }

}
