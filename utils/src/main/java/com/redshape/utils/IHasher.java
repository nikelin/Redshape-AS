package com.redshape.utils;

/**
 * Created by IntelliJ IDEA.
 * User: flare
 * Date: 21.06.2010
 * Time: 12:55:51
 * To change this template use File | Settings | File Templates.
 */
public interface IHasher {
    
    public boolean checkEquality( String origin, String hashed );

    public String hashBase64( Object text );

    public String hash( Object text );

    public String getName();
   
}