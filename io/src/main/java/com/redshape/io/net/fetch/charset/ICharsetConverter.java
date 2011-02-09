package com.redshape.io.net.fetch.charset;


import java.io.InputStream;
import java.io.Reader;

/**
 * @author nikelin
 */
public interface ICharsetConverter<T extends ICharsetMatch> {

    public T detect( byte[] data );

    public Reader getReader( InputStream stream, T charset );

    public String convert( byte[] data );

    public String convert( byte[] data, T charset );

}
