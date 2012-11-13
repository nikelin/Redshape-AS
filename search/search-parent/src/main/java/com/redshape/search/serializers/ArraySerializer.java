/*
 * Copyright 2012 Cyril A. Karpenko
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
