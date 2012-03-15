package com.redshape.utils.hashers;

import com.redshape.utils.IHasher;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by IntelliJ IDEA.
 * User: flare
 * Date: 18.06.2010
 * Time: 17:41:11
 * To change this template use File | Settings | File Templates.
 */
public class MD5 implements IHasher {

    public String getName() {
        return "MD5";
    }

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for ( int i = 0; i < data.length; i++ ) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ( (0 <= halfbyte) && (halfbyte <= 9) ) {
                    buf.append((char) ('0' + halfbyte));
                }
                else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public boolean checkEquality( String origin, String hashed ) {
        try {
            return hash(origin).equals( hashed );
        } catch ( Throwable e ) {
            return false;
        }
    }

    @Override
    public String hashBase64( Object text ) {
        try {
            return new sun.misc.BASE64Encoder().encode( this.digitize(text) );
        } catch ( Throwable e ) {
            throw new IllegalStateException( e.getMessage(), e );
        }
    }

    protected byte[] digitize( Object text ) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update( String.valueOf( text ).getBytes(), 0, String.valueOf( text ).length());
        return md.digest();
    }

    @Override
    public String hash( Object text ) {
        try {
            return convertToHex( this.digitize(text) );
        } catch ( Throwable e ) {
            return null;
        }
    }
}