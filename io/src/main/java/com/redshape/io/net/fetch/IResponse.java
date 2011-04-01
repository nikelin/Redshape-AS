package com.redshape.io.net.fetch;

import java.net.URL;
import java.util.Map;

/**
 * @author nikelin
 */
public interface IResponse {

    public String getBody();

    public URL getUrl();

    public String getContentType();

    public Map<String, String> getHeaders();

}
