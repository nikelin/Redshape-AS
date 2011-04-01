package com.redshape.search.serializers;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 5:17:42 PM
 * To change this template use File | Settings | File Templates.
 */
public class SerializersFactory {
    private static SerializersFactory defaultInstance = new SerializersFactory();
    private Map< Class<? extends ISerializer>, ISerializer> serializers = new HashMap();

    public static void setDefault( SerializersFactory instance ) {
        defaultInstance = instance;
    }

    public static SerializersFactory getDefault() {
        return defaultInstance;
    }

    public ISerializer getSerializer( Class<? extends ISerializer> serializerClazz ) throws InstantiationException {
        ISerializer serializer = this.serializers.get(serializerClazz);
        if ( serializer != null ) {
            return serializer;
        }

        try {
            serializer = serializerClazz.newInstance();
        } catch ( Throwable e ) {
            throw new InstantiationException();
        }

        this.serializers.put( serializerClazz, serializer );

        return serializer;
    }
}
