package com.redshape.search.serializers;

import com.redshape.utils.StringUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by IntelliJ IDEA.
 * User: nikelin
 * Date: Jun 30, 2010
 * Time: 6:39:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ArraySerializer implements ISerializer<String[]> {

    public byte[] serializeBytes( String[] subject ) {
        return this.serializeString(subject).getBytes();
    }

    public String serializeString( String[] subject ) {
        return StringUtils.join( subject, "," );
    }

    public String[] unserialize( String data ) {
        return data.split(",");
    }

    public String[] unserialize( byte[] data ) throws SerializerException {
        try {
            return new BufferedReader( new InputStreamReader( new ByteArrayInputStream(data) ) )
                                .readLine()
                                .split(",");
        } catch ( IOException e ) {
            throw new SerializerException();
        }
    }

}
