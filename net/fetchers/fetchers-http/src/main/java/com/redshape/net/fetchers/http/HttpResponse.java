package com.redshape.net.fetchers.http;

import org.jsoup.Connection;

import com.redshape.net.fetchers.IResponse;

import java.net.URL;
import java.util.Map;

/**
 * @author nikelin
 */
public class HttpResponse implements IResponse {
    private Connection.Response wrapped;

    public HttpResponse( Connection.Response response ) {
        this.wrapped = response;
    }

    public Map<String, String> getHeaders() {
        return this.wrapped.headers();
    }

    public String getContentType() {
        return this.wrapped.contentType();
    }

    public String getBody() {
        return this.wrapped.body();
    }

    public URL getUrl() {
        return this.wrapped.url();
    }

}
