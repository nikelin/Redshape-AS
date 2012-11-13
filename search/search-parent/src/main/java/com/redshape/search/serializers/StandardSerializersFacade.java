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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Cyril A. Karpenko <self@nikelin.ru>
 * @package com.redshape.search.serializers
 * @date 9/5/11 3:38 PM
 */
public class StandardSerializersFacade implements ISerializersFacade {
	private Map<Class<? extends ISerializer>, ISerializer> serializers
			= new HashMap<Class<? extends ISerializer>, ISerializer>();

	public StandardSerializersFacade( Map<Class<? extends ISerializer>, ISerializer> serializers ) {
		this.serializers = serializers;
	}

	@Override
	public <T extends ISerializer> T getSerializer(Class<T> serializerClazz) {
		return (T) this.serializers.get(serializerClazz);
	}
}
