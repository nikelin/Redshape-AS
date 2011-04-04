package com.redshape.ui.data.readers.json;

import com.redshape.ui.data.readers.IReader;
import com.redshape.ui.data.readers.ReaderException;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: 10.01.11
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class JSONReader<V> implements IReader<String, V> {

    protected Object processObject( Object object ) throws ReaderException {
        if ( object instanceof String ) {
            return this.process( (String) object );
        } else if ( object instanceof JSONObject ) {
            return this.processObject( (JSONObject) object );
        } else if ( object instanceof JSONArray ) {
            return this.processObject( (JSONArray) object );
        } else if ( object instanceof Number ) {
            return object;
        }

        throw new ReaderException();
    }

    @SuppressWarnings("unchecked")
	@Override
    public V process( String source ) throws ReaderException {
        JSON json = JSONSerializer.toJSON( source );
        if ( json.isArray() ) {
            return (V) this.processObject( (JSONArray) json);
        } else {
            if ( json instanceof JSONObject ) {
                return (V) this.processObject( (JSONObject) json );
            } else if ( json.isEmpty() ) {
                return null;
            }
        }

        return (V) source;
    }

    protected Map<Object, Object> processObject( JSONObject json ) throws ReaderException {
        Map<Object, Object> result = new HashMap<Object, Object>();
        for ( Object key : json.keySet() ) {
            result.put( key, this.processObject(key) );
        }

        return result;
    }

    @SuppressWarnings({ "deprecation", "unchecked" })
	protected Collection<Object> processObject( JSONArray array ) {
        return JSONArray.toList( array );
    }

}
