package com.redshape.io.protocols.http.hydrators;

import com.redshape.io.protocols.core.request.RequestException;
import com.redshape.io.protocols.core.request.RequestHeader;

import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:23:19 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpRequestHydrator implements IHttpRequestHydrator {
    private static final Logger log = Logger.getLogger( HttpRequestHydrator.class );
    private String[] protocolInfo;
    private String[] headers;
    private String body;

    @Override
    public void parse( String data ) throws RequestException {
        log.info(data);
        int headersEnd = data.indexOf("\n\n");

        this.headers = data.substring( 0, headersEnd - data.length() ).split("\n");

        this.protocolInfo = this.headers[0].split(" ");
        if ( this.protocolInfo.length < 3 ) {
            throw new RequestException();
        }
        
        this.body = data.substring( headersEnd );
    }

    @Override
    public Map<String, String> readParams() {
        Map<String, String> result = new HashMap<String, String>();
        result.putAll( this.processQS( this.readUri() ) );
        result.putAll( this.processQS( this.readBody() ) );

        return result;
    }

    private Map<String, String> processQS( String query ) {
        Map<String, String> result = new HashMap<String, String>();
        if ( !query.isEmpty() && query.contains("?") ) {
            int queryPartStart = query.indexOf("?");
            int queryPartLength = queryPartStart - query.length();

            String queryString = query.substring(queryPartStart + 1, queryPartLength);
            for ( String param : queryString.split("&") ) {
                int delimiterPos = param.indexOf("=");

                result.put( param.substring( 0, delimiterPos), param.substring( delimiterPos, delimiterPos - param.length() ) );
            }
        }

        return result;
    }

    @Override
    public Collection<RequestHeader> readHeaders() {
        Set<RequestHeader> result = new HashSet<RequestHeader>();
        for ( String headerItem : headers ) {
            String[] headerParts = headerItem.split(":");
            if ( headerParts.length != 2 ) {
                continue;
            }

            result.add( new RequestHeader( headerParts[0], headerParts[1] ) );
        }

        return result;
    }

    @Override
    public String readUri() {
        return this.protocolInfo[1];
    }

    @Override
    public String readBody() {
        return this.body;
    }

    @Override
    public String readProtocolVersion() {
        return this.protocolInfo[this.protocolInfo.length - 1];
    }

    @Override
    public String readMethod() {
        return this.protocolInfo[0];
    }
}
