package com.redshape.search.serializers;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 29, 2010
 * Time: 5:19:51 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ISerializer<T> {

    public String serializeString( T object );

    public byte[] serializeBytes( T object );

    public T unserialize( String data ) throws SerializerException; 

    public T unserialize( byte[] data ) throws SerializerException;

}
