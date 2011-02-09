package com.redshape.io.net.fetch.charset.tika;

import org.apache.tika.parser.txt.CharsetDetector;
import org.apache.tika.parser.txt.CharsetMatch;

import com.redshape.io.net.fetch.charset.ICharsetConverter;

import java.io.InputStream;
import java.io.Reader;

/**
 * @author nikelin
 */
public class TikaConverter implements ICharsetConverter<MatchResult> {
    private CharsetDetector detector;

    public TikaConverter() {
        this.detector = new CharsetDetector();
    }

    synchronized public MatchResult detect( byte[] data ) {
        this.detector.setText( data );

        return this.wrapResult(this.detector.detect());
    }

    public Reader getReader( InputStream stream, MatchResult declaredCharset ) {
        return this.detector.getReader( stream, declaredCharset.getCharset() );
    }

    public String convert( byte[] data ) {
        return this.convert( data, this.detect(data) );
    }

    public String convert( byte[] data, MatchResult charset ) {
        return this.detector.getString( data, charset.getCharset() );
    }

    protected MatchResult wrapResult( CharsetMatch match ) {
        return new MatchResult(match);
    }

}
