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

package com.redshape.persistence.dao.serialization.visitors;

import java.net.MalformedURLException;
import java.net.URL;

import com.redshape.persistence.dao.serialization.AbstractSerializerVisitor;

public class URLSerializer extends AbstractSerializerVisitor<URL> {

	@Override
    public byte[] serialize(URL object) {
	    if (object == null || !(object instanceof URL))
	    	return null;
	    return object.toString().getBytes();
    }

	@Override
    public URL deserialize(byte[] value) {
	    String val = new String(value);
	    try {
	        return new URL(val);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed URL in database found, cannot deserialize", e);
        }
    }

	@Override
    public Class<?> getEntityType() {
	    return URL.class;
    }

}
