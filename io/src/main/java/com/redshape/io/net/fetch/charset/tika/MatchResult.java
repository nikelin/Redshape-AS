package com.redshape.io.net.fetch.charset.tika;

import org.apache.tika.parser.txt.CharsetMatch;

import com.redshape.io.net.fetch.charset.ICharsetMatch;

/**
 * @author nikelin
 */
public class MatchResult implements ICharsetMatch<String> {
    private CharsetMatch match;

    public MatchResult( CharsetMatch match ) {
        this.match = match;
    }

    public String getCharset() {
        return this.match.getName();
    }

}
