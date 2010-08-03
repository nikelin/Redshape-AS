package com.vio.io.protocols.http.request;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jul 31, 2010
 * Time: 3:41:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class HttpCode {
    public final static HttpCode CONTINUE_100 = new HttpCode(100, "Continue");
    public final static HttpCode SWITCH_PROTOCOL_101 = new HttpCode(101, "Switching protocol");
    public final static HttpCode OK_200 = new HttpCode(200, "OK");
    public final static HttpCode CREATED_201 = new HttpCode(201, "Created");
    public final static HttpCode ACCEPTED_202 = new HttpCode(202, "Accepted");
    public final static HttpCode NO_AUTH_INFO_203 = new HttpCode(203, "Not-Authoritive Information");
    public final static HttpCode NO_CONTENT_204 = new HttpCode(204, "No content");
    public final static HttpCode RESET_CONTENT_205 = new HttpCode(205, "Reset content");
    public final static HttpCode PARTIAL_CONTENT_206 = new HttpCode(206, "Partial content");
    public final static HttpCode MULTI_CHOICE_300 = new HttpCode(300, "Multi choice");
    public final static HttpCode MOVED_PERM_301 = new HttpCode(301, "Moved permanently");
    public final static HttpCode FOUND_302 = new HttpCode(302, "Found");
    public final static HttpCode SEE_OTHER_303 = new HttpCode(303, "See other");
    public final static HttpCode NOT_MODIFIED_304 = new HttpCode(304, "Not modified");
    public final static HttpCode USE_PROXY_305 = new HttpCode(305, "Use proxy");
    public final static HttpCode UNUSED_306 = new HttpCode(306, "Unused");
    public final static HttpCode TEMP_REDIR_307 = new HttpCode(307, "Temporary redirect");
    public final static HttpCode BAD_REQUEST_400 = new HttpCode(400, "Bad request");
    public final static HttpCode UNAUTHORIZED_401 = new HttpCode(401, "Unauthorized");
    public final static HttpCode PAYMENT_REQUIRED_402 = new HttpCode(402, "Payment required");
    public final static HttpCode FORBIDDEN_403 = new HttpCode(403, "Forbidden");
    public final static HttpCode NOT_FOUND_404 = new HttpCode(404, "Not found");
    public final static HttpCode METHOD_NOT_ALLOW_405 = new HttpCode(405, "Method not allowed");
    public final static HttpCode NOT_ACCEPTABLE_406 = new HttpCode(406, "Not acceptable");
    public final static HttpCode PROXY_AUTH_REQ_407 = new HttpCode(407, "Proxy authentication required");
    public final static HttpCode REQUEST_TIMEOUT_408 = new HttpCode(408, "Request timeout");
    public final static HttpCode CONFLICT_409 = new HttpCode(409, "Conflict");
    public final static HttpCode GONE_410 = new HttpCode(410, "Gone");
    public final static HttpCode INTERNAL_500 = new HttpCode(500, "Internal exception");
    public final static HttpCode BAD_502 = new HttpCode(502, "Bad");

    private int code;
    private String reason;

    private HttpCode( int code, String reason ) {
        this.code = code;
        this.reason = reason;
    }

    public int getCode() {
        return this.code;
    }

    public String getReason() {
        return this.reason;
    }

    public static HttpCode valueOf( int code ) {
        try {
            for ( Field field : HttpCode.class.getDeclaredFields() ) {
                if ( Modifier.isStatic( field.getModifiers() )
                        && HttpCode.class.isAssignableFrom( field.getType() ) ) {
                    HttpCode value = (HttpCode) field.get( HttpCode.class );
                    if ( value  == null ) {
                        continue;
                    }

                    if ( value.code == code ) {
                        return value;
                    }
                }
            }
        } catch ( Throwable e ) {
            return null;
        } finally {
            return null;
        }
    }

    public String toString() {
        return String.valueOf( this.code );
    }

}
