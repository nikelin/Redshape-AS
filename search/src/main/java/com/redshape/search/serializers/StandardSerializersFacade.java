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
