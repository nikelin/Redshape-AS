package com.redshape.search.serializers;

import com.redshape.utils.SimpleStringUtils;

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
public class ArraySerializer implements ISerializer {

	@Override
    public byte[] serializeBytes( Object subject ) {
		this.checkAssertions(subject);

        return this.serializeString(subject).getBytes();
    }

	@Override
    public String serializeString( Object subject ) {
		this.checkAssertions(subject);

        return SimpleStringUtils.join((Object[]) subject, ",");
    }

	protected void checkAssertions( Object subject ) {
		if ( !subject.getClass().isArray() ) {
			throw new IllegalArgumentException("Only array types is acceptable");
		}
	}

	@Override
    public String[] unserialize( String data ) {
        return data.split(",");
    }

	@Override
    public String[] unserialize( byte[] data ) throws SerializerException {
        try {
            return this.unserialize(
				new BufferedReader( new InputStreamReader( new ByteArrayInputStream(data) ) )
					.readLine()
			);
        } catch ( IOException e ) {
            throw new SerializerException();
        }
    }

}
